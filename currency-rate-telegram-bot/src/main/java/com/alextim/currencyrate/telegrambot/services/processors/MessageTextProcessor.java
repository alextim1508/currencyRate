package com.alextim.currencyrate.telegrambot.services.processors;

import com.alextim.currencyrate.telegrambot.model.MessageTextProcessorResult;

public interface MessageTextProcessor {
    MessageTextProcessorResult process(String msgText);
}
