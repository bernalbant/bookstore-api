package com.bookstore.converter;

import com.bookstore.model.enums.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusConverter {

  public OrderStatus convert(int orderStatusValue) {
    switch (orderStatusValue) {
      case 2:
        return OrderStatus.COMPLETED;
      case 1:
        return OrderStatus.SHIPPED;
      case 0:
        return OrderStatus.ORDERED;
      default:
        return OrderStatus.CANCELED;
    }
  }

  public int convert(OrderStatus orderStatus) {
    return orderStatus.getValue();
  }
}