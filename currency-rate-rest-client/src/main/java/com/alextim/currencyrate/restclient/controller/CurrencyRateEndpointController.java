package com.alextim.currencyrate.restclient.controller;

import com.alextim.currencyrate.restclient.model.CurrencyRate;
import com.alextim.currencyrate.restclient.model.RateType;
import com.alextim.currencyrate.restclient.service.CurrencyRateEndpointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "${app.rest.api.prefix}/v1")
public class CurrencyRateEndpointController {

    public final CurrencyRateEndpointService currencyRateEndpointService;

    @GetMapping("/currencyRate/{type}/{currency}/{date}")
    public Mono<CurrencyRate> getCurrencyRate(@PathVariable("type") RateType type,
                                              @PathVariable("currency") String currency,
                                              @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date)  {
        log.info("getCurrencyRate, currency:{}, date:{}", currency, date);

        return currencyRateEndpointService.getCurrencyRate(type, currency, date);
    }
}