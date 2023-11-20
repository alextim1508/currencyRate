package com.alextim.currencyrate.cbrrate.parser;

import com.alextim.currencyrate.cbrrate.model.CurrencyRate;

import java.util.List;

public interface CurrencyRateParser {

    List<CurrencyRate> parse(String ratesAsString);
}