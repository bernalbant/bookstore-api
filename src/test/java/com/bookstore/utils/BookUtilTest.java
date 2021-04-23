package com.bookstore.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bookstore.model.entity.Book;
import com.bookstore.model.request.OrderDetailRequest;
import com.bookstore.model.request.OrderRequest;
import com.bookstore.service.BookService;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookUtilTest {

  @InjectMocks
  private BookUtil bookUtil;

  @Mock
  private BookService bookService;

  @ParameterizedTest
  @MethodSource("provideOrderDetailRequest")
  public void it_should_return_total_book_price(OrderRequest orderRequest, Book book, double price) {
    when(bookService.findById(Mockito.anyInt())).thenReturn(book);

    double totalBookPrice = bookUtil.getTotalBookPrice(orderRequest);

    verify(bookService).findById(Mockito.eq(book.getId()));
    assertThat(totalBookPrice).isEqualTo(price);
  }

  public static Stream<Arguments> provideOrderDetailRequest() {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setCustomerId(123);

    OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
    orderDetailRequest.setBookId(3);
    orderDetailRequest.setQty(2);

    orderRequest.setOrderDetails(List.of(orderDetailRequest));

    Book book = new Book();
    book.setId(3);
    book.setPrice(14.5);

    OrderRequest orderRequest1 = new OrderRequest();
    orderRequest1.setCustomerId(123);

    OrderDetailRequest orderDetailRequest1 = new OrderDetailRequest();
    orderDetailRequest1.setBookId(7);
    orderDetailRequest1.setQty(2);

    orderRequest1.setOrderDetails(List.of(orderDetailRequest1));

    Book book1 = new Book();
    book1.setId(7);
    book1.setPrice(17.5);

    return Stream.of(
        Arguments.of(orderRequest, book, 29),
        Arguments.of(orderRequest1, book1, 35)
    );
  }
}
