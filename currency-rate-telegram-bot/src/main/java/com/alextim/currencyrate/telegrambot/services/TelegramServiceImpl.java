package com.alextim.currencyrate.telegrambot.services;

import com.alextim.currencyrate.telegrambot.clients.TelegramClient;
import com.alextim.currencyrate.telegrambot.model.GetUpdatesRequest;
import com.alextim.currencyrate.telegrambot.model.GetUpdatesResponse;
import com.alextim.currencyrate.telegrambot.model.MessageTextProcessorResult;
import com.alextim.currencyrate.telegrambot.model.SendMessageRequest;
import com.alextim.currencyrate.telegrambot.services.processors.MessageTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.alextim.currencyrate.telegrambot.model.GetUpdatesResponse.*;


@Slf4j
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private final TelegramClient telegramClient;
    private final MessageTextProcessor processorGeneral;
    private final LastUpdateIdKeeper lastUpdateIdKeeper;

    @Override
    public void getUpdates() {
        log.info("getUpdates");

        long offset = lastUpdateIdKeeper.get();
        log.info("offset: {}", offset);

        var request = new GetUpdatesRequest(offset);
        log.info("request: {}", request);

        telegramClient.getUpdates(request)
                .flatMapMany(new Function<GetUpdatesResponse, Publisher<Response>>() {
                    @Override
                    public Publisher<Response> apply(GetUpdatesResponse response) {
                        return Flux.fromIterable(response.getResult());
                    }
                })
                .flatMap(new Function<Response, Publisher<Void>>() {
                    @Override
                    public Publisher<Void> apply(Response response) {
                        log.info("response: {}", response);

                        Message message = response.getMessage();

                        return processorGeneral.process(message.getText())
                                .flatMap(new Function<MessageTextProcessorResult, Mono<Void>>() {
                                    @Override
                                    public Mono<Void> apply(MessageTextProcessorResult result) {
                                        log.info("result: {}", result);

                                        var chatId = message.getChat().getId();
                                        var messageId = message.getMessageId();

                                        var replay = result.getFailReply() == null ? result.getOkReply() : result.getFailReply();
                                        var sendMessageRequest = new SendMessageRequest(chatId, replay, messageId);

                                        return telegramClient.sendMessage(sendMessageRequest)
                                                .doOnNext(new Consumer<String>() {
                                                    @Override
                                                    public void accept(String s) {
                                                        log.info("lastUpdateIdKeeper set: {}", s);
                                                        lastUpdateIdKeeper.set(response.getUpdateId() + 1);
                                                    }
                                                })
                                                .then(Mono.<Void>empty());
                                    }
                                });
                    }
                })
                .subscribe();
    }
}
