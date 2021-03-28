package com.bookstore.model.response;

import lombok.Data;

@Data
public class CustomerResponse {

  private int id;
  private String name;
  private String surname;
  private String phone;
  private String email;
  private String address;
}
