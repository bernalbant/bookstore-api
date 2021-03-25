package com.bookstore.model.enums;

public enum OrderStatus {
  ORDERED(0),
  SHIPPED(1),
  CANCELED(-1),
  COMPLETED(2);

  private final int value;

  OrderStatus(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
