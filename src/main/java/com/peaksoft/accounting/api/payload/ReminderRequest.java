package com.peaksoft.accounting.api.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReminderRequest {
    private LocalDateTime localDateTime;
    private int day;
    private Long productId;
}
