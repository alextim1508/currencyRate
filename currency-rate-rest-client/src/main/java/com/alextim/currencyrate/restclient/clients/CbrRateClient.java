package com.alextim.currencyrate.restclient.clients;

import com.alextim.currencyrate.restclient.config.CbrRateClientConfig;
import com.alextim.currencyrate.restclient.model.CurrencyRate;
import com.alextim.currencyrate.tools.client.ReactiveCustomHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service("cbr")
@RequiredArgsConstructor
@Slf4j
public class CbrRateClient implements RateClient {
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final CbrRateClientConfig config;

    private final ReactiveCustomHttpClient httpClient;

    @Override
    public Mono<CurrencyRate> getCurrencyRate(String currency, LocalDate date) {
        log.info("getCurrencyRate currency:{}, date:{}", currency, date);
        var urlWithParams = String.format("%s/%s/%s", config.getUrl(), currency, DATE_FORMATTER.format(date));

        return httpClient.performGetRequest(urlWithParams, CurrencyRate.class);
    }
}
