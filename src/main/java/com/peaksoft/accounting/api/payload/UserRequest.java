package com.peaksoft.accounting.api.payload;
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
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String repeatPassword;
    private String businessName;
    private Long businessAreaId;
    private String address;
}
