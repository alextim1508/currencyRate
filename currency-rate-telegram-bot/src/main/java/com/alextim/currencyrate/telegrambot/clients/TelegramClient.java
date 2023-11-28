package com.alextim.currencyrate.telegrambot.clients;


import com.alextim.currencyrate.telegrambot.model.GetUpdatesRequest;
import com.alextim.currencyrate.telegrambot.model.GetUpdatesResponse;
import com.alextim.currencyrate.telegrambot.model.SendMessageRequest;

public interface TelegramClient {

    GetUpdatesResponse getUpdates(GetUpdatesRequest request);

    void sendMessage(SendMessageRequest request);
}
