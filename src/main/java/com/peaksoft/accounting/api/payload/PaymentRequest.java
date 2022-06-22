package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peaksoft.accounting.enums.TypeOfPay;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentRequest {
    @NotNull
    private Long productId;
    private String paymentDate;
    private String paymentFile;
    private TypeOfPay typeOfPay;
    private Long bankAccount;
    private double amountOfMoney;
    private String comment;
}

