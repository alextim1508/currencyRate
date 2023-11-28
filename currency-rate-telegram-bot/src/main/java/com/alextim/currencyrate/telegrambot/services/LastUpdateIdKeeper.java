package com.alextim.currencyrate.telegrambot.services;

public interface LastUpdateIdKeeper {
    long get();

    void set(long lastUpdateId);
}
