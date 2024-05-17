package com.alextim.currencyrate.telegrambot.clients;

import com.alextim.currencyrate.telegrambot.model.CurrencyRate;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface CurrencyRateClient {

    Mono<CurrencyRate> getCurrencyRate(String rateType, String currency, LocalDate date);
}
