package com.alextim.currencyrate.tools.client;

import reactor.core.publisher.Mono;

public interface ReactiveCustomHttpClient {
    <T> Mono<T> performGetRequest(String url, Class<T> c) ;

    <T> Mono<T> performPostRequest(String url, Object params, Class<T> c) ;
}
