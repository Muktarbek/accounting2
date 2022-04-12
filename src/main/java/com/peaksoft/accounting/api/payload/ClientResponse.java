package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ClientResponse {
    private String clientId;
    private String companyName;
    private String clientName;
    private String email;
    private String phoneNumber;
    private String address;
    private List<TagResponse> tags;
    private LocalDateTime created;
    private boolean income;
}
