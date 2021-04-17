package com.bookstore.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookstore.model.entity.Order;
import com.bookstore.model.entity.OrderDetail;
import com.bookstore.model.enums.OrderStatus;
import com.bookstore.model.response.OrderDetailResponse;
import com.bookstore.model.response.OrderResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderConverterTest {

  @Mock
  private OrderDetailConverter orderDetailConverter;

  @Mock
  private OrderStatusConverter orderStatusConverter;

  @InjectMocks
  private OrderConverter orderConverter;

  @Test
  public void itShouldConvertOrderToOrderResponse() {
    Order order = new Order();
    order.setId(1);
    order.setCustomerId(12);
    order.setTotalPrice(25.2);
    order.setOrderDate("11.02.2020");
    order.setStatus(2);

    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setBookId(1);
    orderDetail.setCount(12);
    orderDetail.setPrice(11.5);
    order.setOrderDetails(List.of(orderDetail));

    OrderResponse orderResponse = new OrderResponse();
    orderResponse.setId(1);
    orderResponse.setCustomerId(3);
    orderResponse.setTotalPrice(24.5);
    orderResponse.setOrderDate("12.02.2020");
    orderResponse.setStatus(OrderStatus.COMPLETED.toString());

    OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
    orderDetailResponse.setBookId(1);
    orderDetailResponse.setCount(12);
    orderDetailResponse.setPrice(11.5);
    orderResponse.setOrderDetails(List.of(orderDetailResponse));

    Mockito.when(orderDetailConverter.convert(Mockito.any())).thenReturn(orderDetailResponse);
    Mockito.when(orderStatusConverter.convert(order.getStatus())).thenReturn(OrderStatus.COMPLETED);

    OrderResponse orderResponseConversion = orderConverter.convert(order);

    Mockito.verify(orderDetailConverter).convert(orderDetail);
    assertThat(orderResponseConversion).isNotNull();
    assertThat(orderResponseConversion.getId()).isEqualTo(1);
    assertThat(orderResponseConversion.getCustomerId()).isEqualTo(12);
    assertThat(orderResponseConversion.getTotalPrice()).isEqualTo(25.2);
    assertThat(orderResponseConversion.getOrderDate()).isEqualTo("11.02.2020");
    assertThat(orderResponseConversion.getStatus()).isEqualTo("COMPLETED");

    OrderDetailResponse orderDetailConversionResponse = orderResponseConversion.getOrderDetails().get(0);
    assertThat(orderDetailConversionResponse).isNotNull();
    assertThat(orderDetailConversionResponse.getPrice()).isEqualTo(11.5);
    assertThat(orderDetailConversionResponse.getCount()).isEqualTo(12);
    assertThat(orderDetailConversionResponse.getBookId()).isEqualTo(1);
  }
}
