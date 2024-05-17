package com.alextim.currencyrate.cbrrate.service;

import com.alextim.currencyrate.cbrrate.config.CbrConfig;
import com.alextim.currencyrate.cbrrate.model.CachedCurrencyRates;
import com.alextim.currencyrate.cbrrate.model.CurrencyRate;
import com.alextim.currencyrate.cbrrate.parser.CurrencyRateParser;
import com.alextim.currencyrate.cbrrate.requester.CbrRequester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyRateService {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final CbrRequester cbrRequester;
    private final CurrencyRateParser currencyRateParser;
    private final CbrConfig cbrConfig;
    private final Cache<LocalDate, CachedCurrencyRates> currencyRateCache;

    public Mono<CurrencyRate> getCurrencyRate(String currency, LocalDate date) {
        log.info("getCurrencyRate. currency:{}, date:{}", currency, date);

        CachedCurrencyRates cachedCurrencyRates = currencyRateCache.get(date);

        if (cachedCurrencyRates == null) {
            String urlWithParams = String.format("%s?date_req=%s", cbrConfig.getUrl(), DATE_FORMATTER.format(date));

            return cbrRequester.getRatesAsXml(urlWithParams)
                    .map(s -> {
                        List<CurrencyRate> rates = currencyRateParser.parse(s);

                        currencyRateCache.put(date, new CachedCurrencyRates(rates));

                        return filter(currency, rates, date);
                    });
        } else {
            List<CurrencyRate> rates = cachedCurrencyRates.getCurrencyRates();
            return Mono.just(filter(currency, rates, date));
        }
    }

    private static CurrencyRate filter(String currency, List<CurrencyRate> rates, LocalDate date) {
        return rates.stream().filter(rate -> currency.equals(rate.getCharCode()))
                .findFirst()
                .orElseThrow(() ->
                        new CurrencyRateNotFoundException("Currency Rate not found. Currency:" + currency + ", date:" + date));
    }
}