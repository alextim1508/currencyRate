package com.alextim.currencyrate.cbrrate.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CbrConfig.class)
public class ApplicationConfig {
}
