package com.alextim.currencyrate.tools.client;

import com.alextim.currencyrate.tools.exception.HttpClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpClient.newHttpClient;

@Service
@Slf4j
public class JdkHttpClient implements CustomHttpClient {

    @Override
    public String performRequest(String url, String params) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(params))
                .build();
        return doRequest(url, request);
    }

    @Override
    public String performRequest(String url) {
        log.info("http request, url:{}", url);
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        return doRequest(url, request);
    }

    private String doRequest(String url, HttpRequest request) {
        try {
            var client = newHttpClient();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            log.error("Http request error, url:{}", url, ex);
            throw new HttpClientException(ex.getMessage());
        }
    }
}
