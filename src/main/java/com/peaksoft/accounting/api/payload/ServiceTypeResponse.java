package com.peaksoft.accounting.api.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServiceTypeResponse {
    private Long service_type_id;
    private String service_type;
}
