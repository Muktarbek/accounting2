package com.peaksoft.accounting.enums;

import lombok.Getter;

@Getter
public enum InvoiceStatus {
    EXPIRED("Истек"),
    PAID("Оплачен"),
    NOT_PAID("Неоплачен"),
    PARTIALLY("Частично");
    private String invoiceStatus;
    InvoiceStatus(String invoiceStatus){
        this.invoiceStatus = invoiceStatus;}
}
