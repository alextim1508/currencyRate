package com.alextim.currencyrate.telegrambot.clients;

public interface HttpClient {

    String performRequest(String url, String params);

    String performRequest(String url);
}
