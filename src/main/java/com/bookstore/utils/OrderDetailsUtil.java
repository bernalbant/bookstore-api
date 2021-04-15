package com.bookstore.utils;

import com.bookstore.model.entity.Book;
import com.bookstore.model.entity.OrderDetail;
import com.bookstore.model.request.OrderDetailRequest;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.service.BookService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailsUtil {

  private BookService bookService;

  public OrderDetailsUtil(BookService bookService) {
    this.bookService = bookService;
  }

  public List<OrderDetail> getOrderDetails(OrderRequest orderRequest) {
    List<OrderDetail> orderDetails = new ArrayList<>();
    for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
      Book book = bookService.findById(orderDetailRequest.getBookId());
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setBookId(orderDetailRequest.getBookId());
      orderDetail.setCount(orderDetailRequest.getQty());
      orderDetail.setPrice(book.getPrice());
      orderDetails.add(orderDetail);
    }
    return orderDetails;
  }
}
