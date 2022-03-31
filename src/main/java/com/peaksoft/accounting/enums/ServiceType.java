package com.peaksoft.accounting.enums;

import lombok.Getter;

@Getter
public enum ServiceType {
    SERVICE("Сервис"),
    PRODUCT("Продукт");
    private String serviceType;
    ServiceType(String serviceType){this.serviceType = serviceType;}
}
