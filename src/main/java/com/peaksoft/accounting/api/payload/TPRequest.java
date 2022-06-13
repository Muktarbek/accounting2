package com.peaksoft.accounting.api.payload;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peaksoft.accounting.enums.TypeOfPay;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TPRequest {

    private Long productId;
    private String paymentDate;
    private String paymentFile;
    private TypeOfPay typeOfPay;
    private Long categoryId;
    private double amountOfMoney;
    private Boolean isIncomePayment;

}
