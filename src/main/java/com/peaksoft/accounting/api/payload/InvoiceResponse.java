package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peaksoft.accounting.enums.TypeOfPay;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InvoiceResponse {
    private Long invoiceId;
    private String invoiceTitle;
    private ClientResponse client;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<ProductResponse> products;
    private String status;
    private String description;
    private Double sum;
    private LocalDateTime lastDateOfPayment;
    private String typeOfPay;
    private String categoryName;
    private Double discount;
    private Double restAmount;
    private Boolean isIncome;
    private List<PaymentResponse> payments;
}