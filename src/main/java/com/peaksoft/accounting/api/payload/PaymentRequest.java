package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peaksoft.accounting.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentRequest {

    private String payment_date;
    private String payment_file;
    private PaymentMethod payment_method;
    private Long bank_account;
    private double amount_of_money;
    private String comment;

}
