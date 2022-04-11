package com.peaksoft.accounting.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CASH("Наличные"),
    BANK("Оплата через банк"),
    ELECTRONIC_MONEY_TRANSFER("Электронный перевод денег");
    private String paymentMethod;

    PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
