package com.alextim.currencyrate.telegrambot.config;

import com.alextim.currencyrate.telegrambot.clients.TelegramClient;
import com.alextim.currencyrate.telegrambot.services.LastUpdateIdKeeper;
import com.alextim.currencyrate.telegrambot.services.TelegramException;
import com.alextim.currencyrate.telegrambot.services.TelegramService;
import com.alextim.currencyrate.telegrambot.services.TelegramServiceImpl;
import com.alextim.currencyrate.telegrambot.services.processors.MessageTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(CurrencyRateClientConfig.class)
public class ApplicationConfig {
    public static final String TELEGRAM_TOKEN_ENV_NAME = "TELEGRAM_TOKEN";
    public static final String TOKEN_FILE = "TOKEN_FILE";

    @Bean
    public TelegramClientConfig telegramClientConfig(@Value("${app.telegram.url}") String url,
                                                     @Value("${app.telegram.refresh-rate-ms}") int refreshRateMs) {
        var token = System.getProperty(TELEGRAM_TOKEN_ENV_NAME);
        if (token == null) {
            token = System.getenv(TELEGRAM_TOKEN_ENV_NAME);
        }
        if (token == null) {
            var tokenFile = System.getenv(TOKEN_FILE);
            token = readFile(tokenFile);
        }
        if (token == null) {
            log.error("telegram token not found");
            throw new TelegramException("telegram token not found");
        }
        return new TelegramClientConfig(url, token, refreshRateMs);
    }

    private String readFile(String tokenFile) {
        try {
            if (tokenFile != null) {
                return Files.readString(Path.of(tokenFile));
            }
            return null;
        } catch (IOException e) {
            throw new TelegramException("can't read file:" + tokenFile);
        }
    }


    @Bean
    public TelegramImporterScheduled telegramImporterScheduled(TelegramClient telegramClient,
                                                               TelegramClientConfig telegramClientConfig,
                                                               @Qualifier("messageTextProcessorGeneral") MessageTextProcessor processorGeneral,
                                                               LastUpdateIdKeeper lastUpdateIdKeeper) {
        var telegramService = new TelegramServiceImpl(telegramClient, processorGeneral, lastUpdateIdKeeper);
        return new TelegramImporterScheduled(telegramService, telegramClientConfig);
    }

    public static class TelegramImporterScheduled {
        public TelegramImporterScheduled(TelegramService telegramService, TelegramClientConfig telegramClientConfig) {
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(telegramService::getUpdates, 1000, telegramClientConfig.getRefreshRateMs(), TimeUnit.MILLISECONDS);
        }
    }
}
