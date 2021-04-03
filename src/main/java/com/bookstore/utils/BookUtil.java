package com.bookstore.utils;

import com.bookstore.model.entity.Book;
import com.bookstore.model.request.OrderDetailRequest;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.service.BookService;
import org.springframework.stereotype.Component;

@Component
public class BookUtil {

  private BookService bookService;

  public BookUtil(BookService bookService) {
    this.bookService = bookService;
  }

  public Double getTotalBookPrice(OrderRequest orderRequest) {
    double totalPrice = 0;
    for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
      Book book = bookService.findById(orderDetailRequest.getBookId());
      totalPrice += (book.getPrice() * orderDetailRequest.getQty());
    }
    return totalPrice;
  }
}
