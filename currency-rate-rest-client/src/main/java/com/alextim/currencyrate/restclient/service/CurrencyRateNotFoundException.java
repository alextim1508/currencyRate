package com.alextim.currencyrate.restclient.service;

public class CurrencyRateNotFoundException extends RuntimeException {

    public CurrencyRateNotFoundException(String message) {
        super(message);
    }

}
