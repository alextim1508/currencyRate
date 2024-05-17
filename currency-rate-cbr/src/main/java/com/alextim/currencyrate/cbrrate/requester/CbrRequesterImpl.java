package com.alextim.currencyrate.cbrrate.requester;

import com.alextim.currencyrate.tools.client.ReactiveHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CbrRequesterImpl implements CbrRequester {

    private final ReactiveHttpClient httpClient;

    @Override
    public Mono<String> getRatesAsXml(String url) {
        log.info("request for url: {}", url);

        return httpClient.performGetRequest(url, String.class);
    }
}