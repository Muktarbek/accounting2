package com.peaksoft.accounting.api.payload;

import lombok.Data;
import java.util.Set;

@Data
public class LoginResponse {

  private String jwtToken;
  private String message;
  private Set<String> authorities;

}