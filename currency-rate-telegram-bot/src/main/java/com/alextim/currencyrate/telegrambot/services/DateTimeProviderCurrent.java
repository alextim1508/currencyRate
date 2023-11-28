package com.alextim.currencyrate.telegrambot.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateTimeProviderCurrent implements DateTimeProvider {
    @Override
    public LocalDateTime get() {
        return LocalDateTime.now();
    }
}
