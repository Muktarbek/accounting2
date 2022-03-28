package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductRequest {
    @NotNull
    private String productTitle;
    @NotNull
    private Double productPrice;
    @NotNull
    private Long   serviceTypeId;
    @NotNull
    private Long   categoryId;
    @NotNull
    private String productDescription;

}
