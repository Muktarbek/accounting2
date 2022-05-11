package com.peaksoft.accounting.enums;

import lombok.Getter;

@Getter
public enum TypeOfPay {
    CASH("Наличные"),
    BANK("Оплата через банк"),
    ELECTRONIC_MONEY_TRANSFER("Электронный перевод денег");
    private String paymentMethod;

    TypeOfPay(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
