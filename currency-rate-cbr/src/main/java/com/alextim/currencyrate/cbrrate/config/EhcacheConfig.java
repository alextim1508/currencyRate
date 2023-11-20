package com.alextim.currencyrate.cbrrate.config;

import com.alextim.currencyrate.cbrrate.model.CachedCurrencyRates;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class EhcacheConfig {

    @Bean
    public Cache<LocalDate, CachedCurrencyRates> currencyRateCache(@Value("${app.cache.size}") int cacheSize) {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        return cacheManager.createCache("CurrencyRate-Cache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                LocalDate.class,
                                CachedCurrencyRates.class,
                                ResourcePoolsBuilder.heap(cacheSize))
                        .build());
    }
}
