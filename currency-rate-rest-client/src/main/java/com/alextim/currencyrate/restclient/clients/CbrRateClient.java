package com.alextim.currencyrate.restclient.clients;

import com.alextim.currencyrate.restclient.config.CbrRateClientConfig;
import com.alextim.currencyrate.restclient.model.CurrencyRate;
import com.alextim.currencyrate.tools.exception.RequesterException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service("cbr")
@RequiredArgsConstructor
@Slf4j
public class CbrRateClient implements RateClient {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final CbrRateClientConfig config;

    private final ObjectMapper objectMapper;

    @Override
    public CurrencyRate getCurrencyRate(String currency, LocalDate date) {
        log.info("getCurrencyRate currency:{}, date:{}", currency, date);
        var urlWithParams = String.format("%s/%s/%s", config.getUrl(), currency, DATE_FORMATTER.format(date));

        try {
            log.info("request for url:{}", urlWithParams);
            var client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlWithParams))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), CurrencyRate.class);

        } catch (Exception ex) {
            log.error("cbr request error, url:{}", urlWithParams, ex);
            throw new RequesterException(ex);
        }
    }
}
