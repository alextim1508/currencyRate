package com.alextim.currencyrate.tools.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReactiveHttpClient implements ReactiveCustomHttpClient {

    private final WebClient.Builder webBuilder;

    @Override
    public <T> Mono<T> performGetRequest(String url, Class<T> c) {
        log.info("http request, url:{}", url);

        var client = webBuilder.baseUrl(url).build();

        return client
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(c)
                .doOnError(error -> log.error("Http request error, url:{}", url, error))
                .doOnNext(val -> log.info("val: {}", val));
    }


    @Override
    public <T> Mono<T> performPostRequest(String url, Object params, Class<T> c) {
        log.info("http request, url: {}, params: {}", url, params);

        var client = webBuilder.baseUrl(url).build();

        return client
                .post()
                .bodyValue(params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(c)
                .doOnError(error -> log.error("Http request error, url:{}", url, error))
                .doOnNext(val -> log.info("val: {}", val));

    }
}