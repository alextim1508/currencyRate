package com.alextim.currencyrate.telegrambot.config;

import lombok.Value;

@Value
public class TelegramClientConfig {
    String url;
    String token;
    int refreshRateMs;
}
