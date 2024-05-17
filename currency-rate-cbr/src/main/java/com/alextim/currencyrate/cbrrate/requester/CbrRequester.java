package com.alextim.currencyrate.cbrrate.requester;

import reactor.core.publisher.Mono;

public interface CbrRequester {
    Mono<String> getRatesAsXml(String url);
}
