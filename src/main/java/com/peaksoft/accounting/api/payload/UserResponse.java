package com.peaksoft.accounting.api.payload;
import com.peaksoft.accounting.db.entity.BusinessAreaEntity;
import com.peaksoft.accounting.db.entity.CompanyEntity;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String first_name;
    private String last_name;
    private String address;
    private boolean enabled;
    private boolean deleted;
    private BusinessAreaEntity business_area;
    private CompanyEntity company_name;
    private LocalDateTime created;
    private boolean isActive;

}