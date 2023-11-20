package com.alextim.currencyrate.cbrrate.requester;

import com.alextim.currencyrate.tools.exception.RequesterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class CbrRequesterImpl implements CbrRequester {

    @Override
    public String getRatesAsXml(String url) {
        try {
            log.info("request for url:{}", url);
            var client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (Exception ex) {
            log.error("cbr request error, url:{}", url, ex);
            throw new RequesterException(ex);
        }
    }
}