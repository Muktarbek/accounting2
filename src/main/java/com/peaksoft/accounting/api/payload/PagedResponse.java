package com.peaksoft.accounting.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PagedResponse<T,K> {
    List<T> responses;
    K totalPage;
    private Double paymentAmountDays;
    private Double paymentAmountOverdue;
}

