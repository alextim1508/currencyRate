package com.alextim.currencyrate.cbrrate;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication(scanBasePackages = {"com.alextim.currencyrate"})
public class CbrRate {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(CbrRate.class).run(args);
    }
}