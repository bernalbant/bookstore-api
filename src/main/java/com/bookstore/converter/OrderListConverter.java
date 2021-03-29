package com.bookstore.converter;

import com.bookstore.model.entity.Order;
import com.bookstore.model.response.OrderListResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderListConverter {

  private final OrderConverter orderConverter;

  public OrderListConverter(OrderConverter orderConverter) {
    this.orderConverter = orderConverter;
  }

  public OrderListResponse convert(List<Order> orderList) {
    OrderListResponse orderListResponse = new OrderListResponse();

    orderListResponse.setOrderResponseList(orderList
        .stream()
        .map(orderConverter::convert).collect(Collectors.toList()));

    return orderListResponse;
  }
}
