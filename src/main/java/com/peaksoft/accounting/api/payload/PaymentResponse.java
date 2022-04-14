package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peaksoft.accounting.db.entity.BankAccountEntity;
import com.peaksoft.accounting.db.entity.InvoiceEntity;
import com.peaksoft.accounting.enums.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentResponse {

    private Long paymentId;
    private LocalDateTime paymentDate;
    private String paymentFile;
    private PaymentMethod paymentMethod;
    private BankAccountEntity bankAccount;
    private Double amountOfMoney;
    private String comment;
    private LocalDateTime created;
    private InvoiceEntity invoice;
}
