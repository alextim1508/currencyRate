package com.alextim.currencyrate.telegrambot.services.processors;


import org.springframework.stereotype.Service;
import com.alextim.currencyrate.telegrambot.model.MessageTextProcessorResult;
import reactor.core.publisher.Mono;

import static com.alextim.currencyrate.telegrambot.services.processors.Messages.EXPECTED_FORMAT_MESSAGE;

@Service("messageTextProcessorStart")
public class MessageTextProcessorStart implements MessageTextProcessor {
    @Override
    public Mono<MessageTextProcessorResult> process(String msgText) {
        return Mono.just(new MessageTextProcessorResult(EXPECTED_FORMAT_MESSAGE.getText(), null));
    }
}
