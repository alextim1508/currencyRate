package com.alextim.currencyrate.cbrrate.controller;

import com.alextim.currencyrate.cbrrate.model.CurrencyRate;
import com.alextim.currencyrate.cbrrate.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "${app.rest.api.prefix}/v1")
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    @GetMapping("/currencyRate/{currency}/{date}")
    public CurrencyRate getCurrencyRate(@PathVariable("currency") String currency,
                                        @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date)  {
        log.info("getCurrencyRate, currency:{}, date:{}", currency, date);

        var rate = currencyRateService.getCurrencyRate(currency, date);
        log.info("rate:{}", rate);

        return rate;
    }
}