package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductRequest {
    private String productTitle;
    private Double productPrice;
    private Long   serviceTypeId;
    private Long   categoryId;
    private String productDescription;

}
