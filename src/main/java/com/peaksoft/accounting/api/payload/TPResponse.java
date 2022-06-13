package com.peaksoft.accounting.api.payload;
import com.peaksoft.accounting.enums.InvoiceStatus;
import com.peaksoft.accounting.enums.TypeOfPay;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TPResponse {

    private Long tpId;
    private String paymentFile;
    private TypeOfPay typeOfPay;
    private double amountOfMoney;
    private ProductResponse response;
    private LocalDateTime paymentDate;
    private LocalDateTime created;
    private InvoiceStatus status;
    private Boolean isIncomePayment;
}

