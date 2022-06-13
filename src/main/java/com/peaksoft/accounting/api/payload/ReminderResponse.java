package com.peaksoft.accounting.api.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReminderResponse {
    private Long id;
    private LocalDateTime localDateTime;
    private int day;
}
