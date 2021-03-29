package com.bookstore.handler;

import com.bookstore.model.entity.Order;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.model.response.OrderListResponse;
import com.bookstore.model.response.OrderResponse;
import com.bookstore.service.BookService;
import com.bookstore.service.CustomerService;
import com.bookstore.service.OrderService;
import org.springframework.stereotype.Component;


@Component
public class OrderHandler {

  private OrderService orderService;
  private BookService bookService;
  private CustomerService customerService;

  public OrderHandler(OrderService orderService,
                      BookService bookService,
                      CustomerService customerService) {
    this.orderService = orderService;
    this.bookService = bookService;
    this.customerService = customerService;
  }

  public OrderListResponse findAllOrders() {
    return null;
  }

  public OrderResponse findOrderById(int id) {
    return null;
  }

  public OrderListResponse findOrdersByCustomerId(int customerId) {
    return null;
  }

  public OrderResponse createOrder(OrderRequest orderRequest) {
    return null;
  }

  public void deleteOrder(int id) {
    Order order = orderService.findById(id);
    orderService.deleteById(order.getId());
  }
}
