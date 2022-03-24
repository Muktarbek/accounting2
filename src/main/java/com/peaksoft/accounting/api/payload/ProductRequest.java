package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String product_title;
    private Double product_price;
    private Long   service_type_id;
    private Long   category_id;
    private String product_description;

}
