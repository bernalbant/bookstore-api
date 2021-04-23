package com.bookstore.service.impl;

import com.bookstore.exception.NotFoundException;
import com.bookstore.model.entity.Order;
import com.bookstore.model.entity.OrderDetail;
import com.bookstore.repository.OrderRepository;
import com.bookstore.service.OrderServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

  @Mock
  private OrderRepository orderRepository;

  @InjectMocks
  private OrderServiceImpl orderService;

  @ParameterizedTest
  @MethodSource("provideOrders")
  public void itShouldFindAllOrders(Order order) {
    List<Order> orderList = List.of(order);

    Mockito.when(orderRepository.findAll()).thenReturn(orderList);

    List<Order> allOrders = orderService.findAll();

    Mockito.verify(orderRepository).findAll();
    Assertions.assertThat(allOrders).isNotNull();
    Assertions.assertThat(allOrders.get(0).getId()).isEqualTo(order.getId());

    OrderDetail orderDetail = allOrders.get(0).getOrderDetails().get(0);
    Assertions.assertThat(allOrders.get(0).getOrderDetails()).isNotNull();
    Assertions.assertThat(orderDetail.getCount()).isEqualTo(order.getOrderDetails().get(0).getCount());
    Assertions.assertThat(orderDetail.getPrice()).isEqualTo(order.getOrderDetails().get(0).getPrice());
    Assertions.assertThat(orderDetail.getBookId()).isEqualTo(order.getOrderDetails().get(0).getBookId());

    Assertions.assertThat(allOrders.get(0).getCustomerId()).isEqualTo(order.getCustomerId());
    Assertions.assertThat(allOrders.get(0).getOrderDate()).isEqualTo(order.getOrderDate());
    Assertions.assertThat(allOrders.get(0).getTotalPrice()).isEqualTo(order.getTotalPrice());
  }

  @ParameterizedTest
  @MethodSource("provideOrders")
  public void itShouldFindOrderById(Order order) {
    Mockito.when(orderRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(order));

    Order foundOrder = orderService.findById(order.getId());

    Mockito.verify(orderRepository).findById(order.getId());
    Assertions.assertThat(foundOrder).isNotNull();
    Assertions.assertThat(foundOrder.getId()).isEqualTo(order.getId());

    OrderDetail foundOrderDetail = foundOrder.getOrderDetails().get(0);
    Assertions.assertThat(foundOrder.getOrderDetails()).isNotNull();
    Assertions.assertThat(foundOrderDetail.getCount()).isEqualTo(order.getOrderDetails().get(0).getCount());
    Assertions.assertThat(foundOrderDetail.getPrice()).isEqualTo(order.getOrderDetails().get(0).getPrice());
    Assertions.assertThat(foundOrderDetail.getBookId()).isEqualTo(order.getOrderDetails().get(0).getBookId());

    Assertions.assertThat(foundOrder.getCustomerId()).isEqualTo(order.getCustomerId());
    Assertions.assertThat(foundOrder.getOrderDate()).isEqualTo(order.getOrderDate());
    Assertions.assertThat(foundOrder.getTotalPrice()).isEqualTo(order.getTotalPrice());
  }

  @ParameterizedTest
  @MethodSource("provideExceptionOrders")
  public void itShouldThrowNotFoundExceptionWhenThereAreNoAnyOrder(Order order) {
    Mockito.when(orderRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

    NotFoundException notFoundException = Assertions.catchThrowableOfType(() ->
            orderService.findById(order.getId()),
        NotFoundException.class);

    Mockito.verify(orderRepository).findById(order.getId());
    Assertions.assertThat(notFoundException).isNotNull();
    Assertions.assertThat(notFoundException.getMessage()).isEqualTo("Order not found with the given id " + order.getId());
  }

  @ParameterizedTest
  @MethodSource("provideOrders")
  public void itShouldSaveOrder(Order order) {
    Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);

    Order savedOrder = orderService.save(order);

    Mockito.verify(orderRepository).save(order);
    Assertions.assertThat(savedOrder).isNotNull();
    Assertions.assertThat(savedOrder.getId()).isEqualTo(order.getId());

    OrderDetail savedOrderDetail = savedOrder.getOrderDetails().get(0);
    Assertions.assertThat(savedOrder.getOrderDetails()).isNotNull();
    Assertions.assertThat(savedOrderDetail.getCount()).isEqualTo(order.getOrderDetails().get(0).getCount());
    Assertions.assertThat(savedOrderDetail.getPrice()).isEqualTo(order.getOrderDetails().get(0).getPrice());
    Assertions.assertThat(savedOrderDetail.getBookId()).isEqualTo(order.getOrderDetails().get(0).getBookId());

    Assertions.assertThat(savedOrder.getCustomerId()).isEqualTo(order.getCustomerId());
    Assertions.assertThat(savedOrder.getOrderDate()).isEqualTo(order.getOrderDate());
    Assertions.assertThat(savedOrder.getTotalPrice()).isEqualTo(order.getTotalPrice());
  }

  private static Stream<Arguments> provideExceptionOrders() {
    Order order = new Order();
    order.setId(1);
    order.setCustomerId(1);
    order.setTotalPrice(25.2);
    order.setOrderDate("11.02.2020");

    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setBookId(1);
    orderDetail.setCount(12);
    orderDetail.setPrice(11.5);

    order.setOrderDetails(List.of(orderDetail));

    return Stream.of(
        Arguments.of(order)
    );
  }

  private static Stream<Arguments> provideOrders() {
    Order order = new Order();
    order.setId(1);
    order.setCustomerId(1);
    order.setTotalPrice(25.2);
    order.setOrderDate("11.02.2020");

    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setBookId(1);
    orderDetail.setCount(12);
    orderDetail.setPrice(11.5);

    order.setOrderDetails(List.of(orderDetail));

    Order order1 = new Order();
    order1.setId(2);
    order1.setCustomerId(23);
    order1.setTotalPrice(33.5);
    order1.setOrderDate("12.11.2020");

    OrderDetail orderDetail1 = new OrderDetail();
    orderDetail1.setBookId(2);
    orderDetail1.setCount(52);
    orderDetail1.setPrice(44.5);

    order1.setOrderDetails(List.of(orderDetail1));

    Order order2 = new Order();
    order2.setId(3);
    order2.setCustomerId(33);
    order2.setTotalPrice(25.2);
    order2.setOrderDate("01.01.2020");

    OrderDetail orderDetail2 = new OrderDetail();
    orderDetail2.setBookId(3);
    orderDetail2.setCount(33);
    orderDetail2.setPrice(55.5);

    order2.setOrderDetails(List.of(orderDetail2));

    return Stream.of(
        Arguments.of(order),
        Arguments.of(order1),
        Arguments.of(order2)
    );
  }
}
