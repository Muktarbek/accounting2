package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessAreaResponse {
    private Long business_area_id;
    private String area;
}

