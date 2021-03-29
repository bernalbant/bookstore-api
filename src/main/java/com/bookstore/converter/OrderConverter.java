package com.bookstore.converter;

import com.bookstore.model.entity.Order;
import com.bookstore.model.response.OrderDetailResponse;
import com.bookstore.model.response.OrderResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;


@Component
public class OrderConverter {

  private final OrderDetailConverter orderDetailConverter;
  private final OrderStatusConverter orderStatusConverter;

  public OrderConverter(OrderDetailConverter orderDetailConverter,
                        OrderStatusConverter orderStatusConverter) {
    this.orderDetailConverter = orderDetailConverter;
    this.orderStatusConverter = orderStatusConverter;
  }

  public OrderResponse convert(Order order) {
    OrderResponse orderResponse = new OrderResponse();
//        orderResponse.setId(order.getId());
    orderResponse.setCustomerId(order.getCustomerId());
    orderResponse.setTotalPrice(order.getTotalPrice());
    orderResponse.setOrderDate(order.getOrderDate());
    orderResponse.setStatus(orderStatusConverter.convert(order.getStatus()).toString());

    List<OrderDetailResponse> orderDetailResponseList = order.getOrderDetails().stream()
        .map(orderDetailConverter::convert)
        .collect(Collectors.toList());

    orderResponse.setOrderDetails(orderDetailResponseList);

    return orderResponse;
  }
}
