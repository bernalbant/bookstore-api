package com.bookstore.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetail {

  private int bookId;
  private int count;
  private double price;
}
