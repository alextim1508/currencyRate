package com.alextim.currencyrate.restclient.service;

import com.alextim.currencyrate.restclient.clients.RateClient;
import com.alextim.currencyrate.restclient.model.CurrencyRate;
import com.alextim.currencyrate.restclient.model.RateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyRateEndpointService {

    private final Map<String, RateClient> clients;

    public CurrencyRate getCurrencyRate(RateType rateType, String currency, LocalDate date) {
        log.info("getCurrencyRate. rateType:{}, currency:{}, date:{}", rateType, currency, date);
        var client = clients.get(rateType.serviceName);
        return client.getCurrencyRate(currency, date);
    }
}
