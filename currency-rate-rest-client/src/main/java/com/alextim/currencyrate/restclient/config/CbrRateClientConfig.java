package com.alextim.currencyrate.restclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cbr-rate-client")
public class CbrRateClientConfig {
    String url;
}
