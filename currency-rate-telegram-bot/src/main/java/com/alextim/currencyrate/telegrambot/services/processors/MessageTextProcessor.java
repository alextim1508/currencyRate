package com.alextim.currencyrate.telegrambot.services.processors;

import com.alextim.currencyrate.telegrambot.model.MessageTextProcessorResult;
import reactor.core.publisher.Mono;

public interface MessageTextProcessor {
    Mono<MessageTextProcessorResult> process(String msgText);
}
