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

    private Long id;
    private LocalDateTime payment_date;
    private String payment_file;
    private PaymentMethod payment_method;
    private BankAccountEntity bank_account;
    private Double amount_of_money;
    private String comment;
    private LocalDateTime created;
    private InvoiceEntity invoice;
}
