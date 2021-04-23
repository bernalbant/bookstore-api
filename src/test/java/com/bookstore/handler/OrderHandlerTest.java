package com.bookstore.handler;

import com.bookstore.converter.OrderConverter;
import com.bookstore.converter.OrderListConverter;
import com.bookstore.model.entity.Book;
import com.bookstore.model.entity.Customer;
import com.bookstore.model.entity.Order;
import com.bookstore.model.entity.OrderDetail;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.model.response.OrderListResponse;
import com.bookstore.model.response.OrderResponse;
import com.bookstore.service.BookService;
import com.bookstore.service.CustomerService;
import com.bookstore.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderHandlerTest {

  @Mock
  private OrderConverter orderConverter;

  @Mock
  public OrderListConverter orderListConverter;

  @Mock
  private BookService bookService;

  @Mock
  private OrderService orderService;

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private OrderHandler orderHandler;

  @Test
  public void it_should_find_all_orders() {
    ArrayList<Order> orderList = new ArrayList<>();
    Mockito.when(orderService.findAll()).thenReturn(orderList);

    OrderListResponse orderListResponse = new OrderListResponse();
    Mockito.when(orderListConverter.convert(Mockito.any())).thenReturn(orderListResponse);

    orderHandler.findAllOrders();

    Mockito.verify(orderService).findAll();
    Mockito.verify(orderListConverter).convert(new ArrayList<>());
  }

  @Test
  public void it_should_find_order_by_id() {
    Order order = new Order();
    Mockito.when(orderService.findById(Mockito.anyInt())).thenReturn(order);

    OrderResponse orderResponse = new OrderResponse();
    Mockito.when(orderConverter.convert(Mockito.any())).thenReturn(orderResponse);

    orderHandler.findOrderById(1);

    Mockito.verify(orderService).findById(1);
    Mockito.verify(orderConverter).convert(order);
  }

  @Test
  public void it_should_create_order() {
    Order order = new Order();
    order.setId(1);

    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setBookId(1);
    orderDetail.setCount(12);
    orderDetail.setPrice(11.5);
    order.setOrderDetails(List.of(orderDetail));

    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setCustomerId(1);

    Customer customer = new Customer();
    customer.setId(12);

    Mockito.when(orderService.prepareOrder(Mockito.any())).thenReturn(order);

    Book book = new Book();
    book.setId(1);
    book.setStock(12);
    Mockito.when(bookService.findById(Mockito.anyInt())).thenReturn(book);
    Mockito.when(bookService.save(Mockito.any())).thenReturn(book);

    OrderResponse orderResponse = new OrderResponse();
    Mockito.when(orderConverter.convert(Mockito.any())).thenReturn(orderResponse);

    Mockito.when(orderService.save(Mockito.any())).thenReturn(order);

    orderHandler.createOrder(orderRequest);

    Mockito.verify(orderService).save(order);
    Mockito.verify(orderService).prepareOrder(orderRequest);
    Mockito.verify(bookService).findById(book.getId());
    Mockito.verify(bookService).save(book);
    Mockito.verify(orderConverter).convert(order);
  }

  @Test
  public void it_should_delete_order() {
    Order order = new Order();
    order.setId(1);
    Mockito.when(orderService.findById(Mockito.anyInt())).thenReturn(order);

    orderHandler.deleteOrder(order.getId());

    Mockito.verify(orderService).findById(1);
  }

  @Test
  public void it_should_find_orders_with_given_customer_id() {
    int customerId = 1;
    ArrayList<Order> orderList = new ArrayList<>();
    Mockito.when(orderService.findOrdersByCustomerId(Mockito.anyInt())).thenReturn(orderList);

    OrderListResponse orderListResponse = new OrderListResponse();
    Mockito.when(orderListConverter.convert(Mockito.any())).thenReturn(orderListResponse);

    orderHandler.findOrdersByCustomerId(customerId);

    Mockito.verify(orderService).findOrdersByCustomerId(customerId);
    Mockito.verify(orderListConverter).convert(new ArrayList<>());
  }
}
