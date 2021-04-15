package com.bookstore.converter;

import com.bookstore.model.enums.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OrderStatusConverterTest {

  @Autowired
  private OrderStatusConverter orderStatusConverter;

  @BeforeEach
  void init() {
    orderStatusConverter = new OrderStatusConverter();
  }

  @Test
  public void itShouldConvertStatusValueToOrderStatusEnumType() {
    int statusValue = 2;

    var orderStatus = orderStatusConverter.convert(statusValue);
    Assertions.assertThat(orderStatus).isNotNull();
    Assertions.assertThat(orderStatus.getValue()).isEqualTo(2);
    Assertions.assertThat(orderStatus).isEqualTo(OrderStatus.COMPLETED);
  }

  @Test
  public void itShouldConvertOrderStatusEnumTypeToStatusValue() {
    var orderStatusType = OrderStatus.COMPLETED;

    var orderStatusValue = orderStatusConverter.convert(orderStatusType);
    Assertions.assertThat(orderStatusValue).isNotNull();
    Assertions.assertThat(orderStatusValue).isEqualTo(2);
  }
}
