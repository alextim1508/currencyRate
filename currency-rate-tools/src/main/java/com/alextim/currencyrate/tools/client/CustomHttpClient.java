package com.alextim.currencyrate.tools.client;

public interface CustomHttpClient {

    String performRequest(String url, String params);

    String performRequest(String url);
}
