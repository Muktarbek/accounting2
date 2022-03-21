package com.peaksoft.accounting.api.payload;
import com.peaksoft.accounting.db.entity.CompanyEntity;
import com.sun.istack.NotNull;
import lombok.Data;
import javax.validation.constraints.Email;

@Data
public class UserRequest {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String first_name;
    @NotNull
    private String last_name;
    @NotNull
    private String repeat_password;
    private Long business_area_id;
    private String company_name;
    private String address;
}
