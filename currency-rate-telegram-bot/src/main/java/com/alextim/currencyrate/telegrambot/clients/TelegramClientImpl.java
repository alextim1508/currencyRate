package com.alextim.currencyrate.telegrambot.clients;

import com.alextim.currencyrate.telegrambot.config.TelegramClientConfig;
import com.alextim.currencyrate.telegrambot.model.GetUpdatesRequest;
import com.alextim.currencyrate.telegrambot.model.GetUpdatesResponse;
import com.alextim.currencyrate.telegrambot.model.SendMessageRequest;
import com.alextim.currencyrate.tools.client.ReactiveCustomHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramClientImpl implements TelegramClient {

    private final ReactiveCustomHttpClient httpClient;
    private final TelegramClientConfig clientConfig;

    @Override
    public Mono<GetUpdatesResponse> getUpdates(GetUpdatesRequest request) {
        return httpClient.performPostRequest(makeUrl("getUpdates"), request, GetUpdatesResponse.class);
    }

    @Override
    public Mono<String> sendMessage(SendMessageRequest request) {
        return httpClient.performPostRequest(makeUrl("sendMessage"), request, String.class);
    }

    private String makeUrl(String apiRequest) {
        return String.format("%s/bot%s/%s", clientConfig.getUrl(), clientConfig.getToken(), apiRequest);
    }
}
