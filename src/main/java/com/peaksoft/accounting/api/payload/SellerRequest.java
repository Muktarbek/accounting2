package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SellerRequest {

    @NotNull
    private String companyName;
    private String sellerName;
    private String sellerSurname;
    private String email;
    @NotNull
    private String phoneNumber;
    private String address;
}
