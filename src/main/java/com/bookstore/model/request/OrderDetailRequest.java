package com.bookstore.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDetailRequest {

  @NotNull(message = "Book id is required")
  private int bookId;

  @NotNull(message = "Quantity field is required")
  @Min(value = 1, message = "Quantity of book can not be lower than one")
  private int qty;
}