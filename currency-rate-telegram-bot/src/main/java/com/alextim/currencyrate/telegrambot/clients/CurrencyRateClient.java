package com.alextim.currencyrate.telegrambot.clients;

import com.alextim.currencyrate.telegrambot.model.CurrencyRate;

import java.time.LocalDate;

public interface CurrencyRateClient {

    CurrencyRate getCurrencyRate(String rateType, String currency, LocalDate date);
}
