package com.bookstore.converter;

import com.bookstore.model.entity.OrderDetail;
import com.bookstore.model.response.OrderDetailResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OrderDetailConverterTest {

  @Autowired
  private OrderDetailConverter orderDetailConverter;

  @BeforeEach
  void init() {
    orderDetailConverter = new OrderDetailConverter();
  }

  @Test
  public void itShouldConvertOrderDetailToOrderDetailResponse() {
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setBookId(4);
    orderDetail.setCount(8);
    orderDetail.setPrice(12.0);

    OrderDetailResponse orderDetailResponse = orderDetailConverter.convert(orderDetail);

    Assertions.assertThat(orderDetailResponse.getBookId()).isEqualTo(4);
    Assertions.assertThat(orderDetailResponse.getCount()).isEqualTo(8);
    Assertions.assertThat(orderDetailResponse.getPrice()).isEqualTo(12.0);
  }
}
