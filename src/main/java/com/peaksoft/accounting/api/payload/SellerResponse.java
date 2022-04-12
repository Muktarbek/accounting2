package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SellerResponse {

    private Long id;
    private String companyName;
    private String sellerName;
    private String sellerSurname;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isActive;
    private LocalDateTime created;
}
