package com.alextim.currencyrate.telegrambot.clients;

import com.alextim.currencyrate.telegrambot.config.CurrencyRateClientConfig;
import com.alextim.currencyrate.telegrambot.model.CurrencyRate;
import com.alextim.currencyrate.tools.client.ReactiveCustomHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyRateClientImpl implements CurrencyRateClient {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final CurrencyRateClientConfig config;
    private final ReactiveCustomHttpClient httpClient;

    @Override
    public Mono<CurrencyRate> getCurrencyRate(String rateType, String currency, LocalDate date) {
        log.info("getCurrencyRate rateType:{}, currency:{}, date:{}", rateType, currency, date);

        var urlWithParams = String.format("%s/%s/%s/%s", config.getUrl(), rateType, currency, DATE_FORMATTER.format(date));
        log.info("urlWithParams: {}", urlWithParams);

        return httpClient.performGetRequest(urlWithParams, CurrencyRate.class);
    }
}
