package com.alextim.currencyrate.restclient;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication(scanBasePackages = {"com.alextim.currencyrate"})
public class CurrencyRateRestClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(CurrencyRateRestClient.class).run(args);
    }
}