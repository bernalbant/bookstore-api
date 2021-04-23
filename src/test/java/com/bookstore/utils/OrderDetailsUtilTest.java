package com.bookstore.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bookstore.model.entity.Book;
import com.bookstore.model.entity.OrderDetail;
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
class OrderDetailsUtilTest {

  @InjectMocks
  private OrderDetailsUtil orderDetailsUtil;

  @Mock
  private BookService bookService;

  @ParameterizedTest
  @MethodSource("provideOrderDetailRequest")
  public void itShouldGetOrderDetails(OrderRequest orderRequest, Book book) {
    when(bookService.findById(Mockito.anyInt())).thenReturn(book);

    List<OrderDetail> orderDetails = orderDetailsUtil.getOrderDetails(orderRequest);

    verify(bookService).findById(eq(book.getId()));
    assertThat(orderDetails).isNotNull();
    assertThat(orderDetails.get(0).getBookId()).isEqualTo(orderRequest.getOrderDetails().get(0).getBookId());
    assertThat(orderDetails.get(0).getCount()).isEqualTo(orderRequest.getOrderDetails().get(0).getQty());
    assertThat(orderDetails.get(0).getPrice()).isEqualTo(book.getPrice());
  }

  private static Stream<Arguments> provideOrderDetailRequest() {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setCustomerId(123);

    OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
    orderDetailRequest.setBookId(3);
    orderDetailRequest.setQty(13);

    orderRequest.setOrderDetails(List.of(orderDetailRequest));

    Book book = new Book();
    book.setId(3);
    book.setPrice(14.5);

    OrderRequest orderRequest1 = new OrderRequest();
    orderRequest1.setCustomerId(123);

    OrderDetailRequest orderDetailRequest1 = new OrderDetailRequest();
    orderDetailRequest1.setBookId(7);
    orderDetailRequest1.setQty(13);

    orderRequest1.setOrderDetails(List.of(orderDetailRequest1));

    Book book1 = new Book();
    book1.setId(7);
    book1.setPrice(17.5);

    return Stream.of(
        Arguments.of(orderRequest, book),
        Arguments.of(orderRequest1, book1)
    );
  }


}