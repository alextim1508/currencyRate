package com.alextim.currencyrate.restclient.clients;



import com.alextim.currencyrate.restclient.model.CurrencyRate;

import java.time.LocalDate;

public interface RateClient {

    CurrencyRate getCurrencyRate(String currency, LocalDate date);
}
