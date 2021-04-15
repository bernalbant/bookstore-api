package com.bookstore.converter;

import com.bookstore.model.entity.Order;
import com.bookstore.model.response.OrderDetailResponse;
import com.bookstore.model.response.OrderListResponse;
import com.bookstore.model.response.OrderResponse;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderListConverterTest {

  @Mock
  private OrderConverter orderConverter;

  @InjectMocks
  private OrderListConverter orderListConverter;

  @Test
  public void itShouldConvertOrderToOrderListResponse() {
    Order order = new Order();
    order.setId(1);
    order.setCustomerId(3);
    order.setTotalPrice(24.5);
    order.setOrderDate("12.02.2020");

    OrderResponse orderResponse = new OrderResponse();
    orderResponse.setId(1);
    orderResponse.setCustomerId(3);
    orderResponse.setTotalPrice(24.5);
    orderResponse.setOrderDate("12.02.2020");

    OrderDetailResponse orderDetail = new OrderDetailResponse();
    orderDetail.setBookId(1);
    orderDetail.setCount(12);
    orderDetail.setPrice(11.5);
    orderResponse.setOrderDetails(List.of(orderDetail));

    Mockito.when(orderConverter.convert(Mockito.any())).thenReturn(orderResponse);

    OrderListResponse conversionOfOrderList = orderListConverter.convert(List.of(order));

    Mockito.verify(orderConverter).convert(order);
    Assertions.assertThat(conversionOfOrderList).isNotNull();
    OrderResponse orderResponseList = conversionOfOrderList.getOrderResponseList().get(0);
    Assertions.assertThat(orderResponseList.getId()).isEqualTo(1);
    Assertions.assertThat(orderResponseList.getCustomerId()).isEqualTo(3);
    Assertions.assertThat(orderResponseList.getOrderDate()).isEqualTo("12.02.2020");
    Assertions.assertThat(orderResponseList.getTotalPrice()).isEqualTo(24.5);
    Assertions.assertThat(orderResponseList.getOrderDetails()).isNotNull();
  }
}
