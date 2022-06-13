package com.peaksoft.accounting.enums;

public enum ReminderType {
    PAY_FOR("Оплатите"),

    EXPIRED("Истек");
    private String name;
    ReminderType(String name) {
        this.name = name;
    }
}
