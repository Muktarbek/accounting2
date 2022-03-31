package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ClientRequest {
    @NotNull
    private String companyName;
    @NotNull
    private String clientName;
    private String email;
    private String phoneNumber;
    private String address;
    private Long tags;
}
