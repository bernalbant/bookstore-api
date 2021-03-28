package com.bookstore.model.response;

import lombok.Data;

@Data
public class BookResponse {

  private int id;
  private String name;
  private String description;
  private String author;
  private double price;
  private int stock;
}