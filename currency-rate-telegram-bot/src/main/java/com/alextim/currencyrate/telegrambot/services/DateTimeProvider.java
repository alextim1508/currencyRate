package com.alextim.currencyrate.telegrambot.services;

import java.time.LocalDateTime;

public interface DateTimeProvider {
    LocalDateTime get();
}
