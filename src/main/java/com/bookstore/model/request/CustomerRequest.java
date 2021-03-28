package com.bookstore.model.request;

import com.bookstore.validation.ValidEmail;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequest {

  @NotNull(message = "Name field is required")
  @Size(min = 1, message = "Name field is required")
  private String name;

  @NotNull(message = "Last Name field is required")
  @Size(min = 1, message = "Last Name field is required")
  private String surname;

  @NotNull(message = "Phone Number field is required")
  @Pattern(regexp = "^\\d{10}$", message = "Phone Number format is invalid")
  private String phone;

  @NotNull(message = "Email field is required")
  @Size(min = 1, message = "Email field is required")
  @ValidEmail(message = "Email format is invalid")
  private String email;

  private String address;
}