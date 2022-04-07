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
    private String product_title;
    @NotNull
    private Double product_price;
    @NotNull
    private Long   service_type_id;
    @NotNull
    private Long   category_id;
    @NotNull
    private String product_description;

}
