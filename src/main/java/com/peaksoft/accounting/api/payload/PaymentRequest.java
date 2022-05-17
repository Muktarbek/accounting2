package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peaksoft.accounting.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentRequest {
    @NotNull
    private String paymentDate;
    private String paymentFile;
    private PaymentMethod paymentMethod;
    private Long bankAccount;
    private double amountOfMoney;
    private String comment;

}
