package com.bookstore.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

  @NotNull(message = "Username field is required")
  @Size(min = 1, message = "Username field is required")
  private String username;

  @NotNull(message = "Password field is required")
  @Size(min = 1, message = "Password field is required")
  private String password;
}
