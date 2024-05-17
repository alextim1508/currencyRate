package com.alextim.currencyrate.cbrrate.controller;

import com.alextim.currencyrate.cbrrate.model.CurrencyRate;
import com.alextim.currencyrate.cbrrate.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "${app.rest.api.prefix}/v1")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    @GetMapping("/currencyRate/{currency}/{date}")
    public Mono<ResponseEntity<CurrencyRate>> getCurrencyRate(@PathVariable("currency") String currency,
                                                              @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date)  {
        log.info("getCurrencyRate, currency:{}, date:{}", currency, date);

        return currencyRateService.getCurrencyRate(currency, date)
                .map(ResponseEntity::ok)
//                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.BAD_REQUEST).build())
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build());
    }
}