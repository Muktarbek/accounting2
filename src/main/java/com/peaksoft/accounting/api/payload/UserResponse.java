package com.peaksoft.accounting.api.payload;

import com.peaksoft.accounting.db.entity.BusinessAreaEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String businessName;
    private String address;
    private boolean enabled;
    private boolean deleted;
    private BusinessAreaEntity businessArea;
    private LocalDateTime created;
    private boolean isActive;
}