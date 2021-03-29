package com.bookstore.converter;

import com.bookstore.model.entity.OrderDetail;
import com.bookstore.model.response.OrderDetailResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailConverter {

  public OrderDetailResponse convert(OrderDetail orderDetail) {
    OrderDetailResponse orderDetailResponse = new OrderDetailResponse();

    orderDetailResponse.setBookId(orderDetail.getBookId());
    orderDetailResponse.setCount(orderDetail.getCount());
    orderDetailResponse.setPrice(orderDetail.getPrice());

    return orderDetailResponse;
  }
}
