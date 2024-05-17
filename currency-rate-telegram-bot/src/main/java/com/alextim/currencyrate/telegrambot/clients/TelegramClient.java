package com.alextim.currencyrate.telegrambot.clients;


import com.alextim.currencyrate.telegrambot.model.GetUpdatesRequest;
import com.alextim.currencyrate.telegrambot.model.GetUpdatesResponse;
import com.alextim.currencyrate.telegrambot.model.SendMessageRequest;
import reactor.core.publisher.Mono;

public interface TelegramClient {

    Mono<GetUpdatesResponse> getUpdates(GetUpdatesRequest request);

    Mono<String>  sendMessage(SendMessageRequest request);
}
