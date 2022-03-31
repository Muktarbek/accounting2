package com.peaksoft.accounting.api.payload;

import lombok.Data;

import java.util.List;

@Data
public class ClientResponseView {

    private List<ClientResponse> clients;
}
