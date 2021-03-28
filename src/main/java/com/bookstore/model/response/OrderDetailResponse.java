package com.bookstore.model.response;

import lombok.Data;

@Data
public class OrderDetailResponse {

  private int bookId;
  private int count;
  private double price;
}