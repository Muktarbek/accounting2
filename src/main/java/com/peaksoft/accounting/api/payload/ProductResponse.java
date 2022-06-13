package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductResponse {
    private Long productId;
    private String productTitle;
    private double productPrice;
    private ServiceTypeResponse serviceType;
    private CategoryResponse category;
    private String productDescription;
    private int remindDay;
}
