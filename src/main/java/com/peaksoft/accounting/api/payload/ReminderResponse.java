package com.peaksoft.accounting.api.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReminderResponse {
    private Long id;
    private LocalDate localDate;
    private int day;
}
