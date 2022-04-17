package com.peaksoft.accounting.api.payload;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.peaksoft.accounting.db.entity.BusinessAreaEntity;
import com.peaksoft.accounting.db.entity.CompanyEntity;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private boolean enabled;
    private boolean deleted;
    private BusinessAreaEntity businessArea;
    private CompanyEntity companyName;
    private LocalDateTime created;
    private boolean isActive;

}