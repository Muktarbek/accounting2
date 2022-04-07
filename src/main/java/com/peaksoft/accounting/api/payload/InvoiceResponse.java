package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InvoiceResponse {
    private Long invoiceId;
    private String invoiceTitle;
    private ClientResponse client;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<ProductResponse> products;
    private String status;
    private Double sum;
}
