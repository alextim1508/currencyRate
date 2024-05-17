package com.alextim.currencyrate.telegrambot;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication(scanBasePackages = {"com.alextim.currencyrate"})
public class CurrencyRateBot {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(CurrencyRateBot.class).run(args);
    }
}
