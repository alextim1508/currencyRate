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

    public CurrencyRate getCurrencyRate(String currency, LocalDate date) {
        log.info("getCurrencyRate. currency:{}, date:{}", currency, date);
        List<CurrencyRate> rates;

        var cachedCurrencyRates =  currencyRateCache.get(date);
        if (cachedCurrencyRates == null) {

            var urlWithParams = String.format("%s?date_req=%s", cbrConfig.getUrl(), DATE_FORMATTER.format(date));

            var ratesAsXml = cbrRequester.getRatesAsXml(urlWithParams);

            rates = currencyRateParser.parse(ratesAsXml);

            currencyRateCache.put(date, new CachedCurrencyRates(rates));
        } else {
            rates = cachedCurrencyRates.getCurrencyRates();
        }

        return rates.stream().filter(rate -> currency.equals(rate.getCharCode()))
                .findFirst()
                .orElseThrow(() ->
                        new CurrencyRateNotFoundException("Currency Rate not found. Currency:" + currency + ", date:" + date));
    }
}