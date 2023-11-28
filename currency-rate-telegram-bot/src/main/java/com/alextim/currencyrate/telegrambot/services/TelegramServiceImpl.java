package com.alextim.currencyrate.telegrambot.services;

import com.alextim.currencyrate.telegrambot.clients.TelegramClient;
import com.alextim.currencyrate.telegrambot.model.GetUpdatesRequest;
import com.alextim.currencyrate.telegrambot.model.GetUpdatesResponse;
import com.alextim.currencyrate.telegrambot.model.MessageTextProcessorResult;
import com.alextim.currencyrate.telegrambot.model.SendMessageRequest;
import com.alextim.currencyrate.telegrambot.services.processors.MessageTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private final TelegramClient telegramClient;
    private final MessageTextProcessor processorGeneral;
    private final LastUpdateIdKeeper lastUpdateIdKeeper;

    @Override
    public void getUpdates() {
        try {
            log.info("getUpdates");

            long offset = lastUpdateIdKeeper.get();
            log.debug("offset: {}", offset);

            var request = new GetUpdatesRequest(offset);
            log.debug("request: {}", request);

            GetUpdatesResponse response = telegramClient.getUpdates(request);

            long lastUpdateId = processResponse(response);

            lastUpdateId = lastUpdateId == 0 ? offset : lastUpdateId + 1;

            lastUpdateIdKeeper.set(lastUpdateId);
            log.info("lastUpdateId:{}", lastUpdateId);
        } catch (Exception ex) {
            log.error("unhandled exception", ex);
        }
    }

    private long processResponse(GetUpdatesResponse response) {
        log.info("response.getResult().size:{}", response.getResult().size());

        long lastUpdateId = 0;
        for (var responseMsg : response.getResult()) {
            lastUpdateId = Math.max(lastUpdateId, responseMsg.getUpdateId());
            processMessage(responseMsg.getMessage());
        }
        log.info("lastUpdateId:{}", lastUpdateId);
        return lastUpdateId;
    }

    private void processMessage(GetUpdatesResponse.Message message) {
        log.info("message:{}", message);

        var chatId = message.getChat().getId();
        var messageId = message.getMessageId();

        MessageTextProcessorResult result = processorGeneral.process(message.getText());

        var replay = result.getFailReply() == null ? result.getOkReply() : result.getFailReply();
        var sendMessageRequest = new SendMessageRequest(chatId, replay, messageId);

        telegramClient.sendMessage(sendMessageRequest);
    }
}
