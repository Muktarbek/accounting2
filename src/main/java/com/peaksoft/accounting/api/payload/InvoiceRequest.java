package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InvoiceRequest {

    @NotNull
    private String invoiceTitle;
    private Long clientId;
    private String startDate;
    private String endDate;
    private String description;
    private List<Long> productsId;
    private Double sum;
    private Double discount;
}
