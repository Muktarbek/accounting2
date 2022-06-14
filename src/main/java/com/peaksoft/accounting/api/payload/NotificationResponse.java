package com.peaksoft.accounting.api.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class NotificationResponse {
    private String name;
    private String price;
    private LocalDateTime dateTime;
}
