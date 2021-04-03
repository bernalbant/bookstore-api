package com.bookstore.handler;

import com.bookstore.converter.OrderConverter;
import com.bookstore.converter.OrderListConverter;
import com.bookstore.model.entity.Book;
import com.bookstore.model.entity.Order;
import com.bookstore.model.entity.OrderDetail;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.model.response.OrderListResponse;
import com.bookstore.model.response.OrderResponse;
import com.bookstore.service.BookService;
import com.bookstore.service.CustomerService;
import com.bookstore.service.OrderService;
import com.bookstore.utils.StockUtil;
import org.springframework.stereotype.Component;


@Component
public class OrderHandler {

  private OrderService orderService;
  private BookService bookService;
  private CustomerService customerService;
  private OrderConverter orderConverter;
  private OrderListConverter orderListConverter;

  public OrderHandler(OrderService orderService,
                      BookService bookService,
                      CustomerService customerService,
                      OrderConverter orderConverter,
                      OrderListConverter orderListConverter) {
    this.orderService = orderService;
    this.bookService = bookService;
    this.customerService = customerService;
    this.orderConverter = orderConverter;
    this.orderListConverter = orderListConverter;
  }

  public OrderListResponse findAllOrders() {
    return orderListConverter.convert(orderService.findAll());
  }

  public OrderResponse findOrderById(int id) {
    return orderConverter.convert(orderService.findById(id));
  }

  public OrderListResponse findOrdersByCustomerId(int customerId) {
    return orderListConverter.convert(orderService.findOrdersByCustomerId(customerId));
  }

  public OrderResponse createOrder(OrderRequest orderRequest) {
    Order prepareOrder = orderService.prepareOrder(orderRequest);
    for (OrderDetail orderDetail : prepareOrder.getOrderDetails()) {
      Book book = bookService.findById(orderDetail.getBookId());
      StockUtil.updateStock(book, orderDetail.getCount());
      bookService.save(book);
    }

    Order order = orderService.save(prepareOrder);

    return orderConverter.convert(order);
  }

  public void deleteOrder(int id) {
    Order order = orderService.findById(id);
    orderService.deleteById(order.getId());
  }
}
