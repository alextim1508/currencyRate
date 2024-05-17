package com.alextim.currencyrate.restclient.clients;



import com.alextim.currencyrate.restclient.model.CurrencyRate;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface RateClient {

    Mono<CurrencyRate> getCurrencyRate(String currency, LocalDate date);
}
