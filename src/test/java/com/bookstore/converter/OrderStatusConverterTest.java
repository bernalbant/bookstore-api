package com.bookstore.converter;

import static org.assertj.core.api.Assertions.assertThat;

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
    assertThat(orderStatus).isNotNull();
    assertThat(orderStatus.getValue()).isEqualTo(2);
    assertThat(orderStatus).isEqualTo(OrderStatus.COMPLETED);
  }

  @Test
  public void itShouldConvertOrderStatusEnumTypeToStatusValue() {
    var orderStatusType = OrderStatus.COMPLETED;

    var orderStatusValue = orderStatusConverter.convert(orderStatusType);
    assertThat(orderStatusValue).isNotNull();
    assertThat(orderStatusValue).isEqualTo(2);
  }
}
