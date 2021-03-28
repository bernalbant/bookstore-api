package com.bookstore.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class BookRequest {

  @NotNull(message = "Name field is required")
  @Size(min = 1, message = "Name field is required")
  private String name;

  private String description;

  @NotNull(message = "Author field is required")
  @Size(min = 1, message = "Author field is required")
  private String author;

  @NotNull(message = "Price field is required")
  @Min(value = 0, message = "Price can not be lower than zero")
  private double price;

  private int stock;
}