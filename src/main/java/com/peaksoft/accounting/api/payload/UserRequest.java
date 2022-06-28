package com.peaksoft.accounting.api.payload;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.NotNull;
import lombok.Data;
import javax.validation.constraints.Email;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserRequest {
    @Email
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String repeatPassword;
    private Long businessAreaId;
    private String companyName;
    private String address;
}
