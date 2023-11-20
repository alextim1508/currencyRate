package com.alextim.currencyrate.restclient.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RateType {
    CBR("cbr");

    public final String serviceName;
}
