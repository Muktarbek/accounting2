package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
    private Long   product_id;
    private String product_title;
    private Double product_price;
    private ServiceTypeResponse  service_type;
    private CategoryResponse category;
    private String product_description;

}
