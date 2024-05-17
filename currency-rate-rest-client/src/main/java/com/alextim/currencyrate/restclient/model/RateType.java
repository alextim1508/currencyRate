package com.alextim.currencyrate.restclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RateType {
    CBR("cbr");

    public final String serviceName;
}
