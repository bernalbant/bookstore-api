package com.bookstore.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import com.bookstore.exception.InsufficientStockException;
import com.bookstore.model.entity.Book;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StockUtilTest {

  @ParameterizedTest
  @MethodSource("provideBook")
  public void itShouldThrowInsufficientStockExceptionWhenBookStockIsSmallerThanQuantity(Book book) {
    int quantity = 30;

    InsufficientStockException insufficientStockException = catchThrowableOfType(() ->
        StockUtil.updateStock(book, quantity), InsufficientStockException.class);

    assertThat(insufficientStockException).isNotNull();
    assertThat(insufficientStockException.getMessage()).isEqualTo("Insufficient stock");
  }

  @ParameterizedTest
  @MethodSource("provideBook")
  public void itShouldUpdateStockOfBook(Book book) {
    int quantity = 10;

    StockUtil.updateStock(book, quantity);

    assertThat(book.getStock()).isEqualTo(10);
  }

  public static Stream<Arguments> provideBook() {
    Book book = new Book();
    book.setId(24);
    book.setName("Design Patterns");
    book.setAuthor("Erich Gamma");
    book.setDescription("Elements of Reusable Object-Oriented");
    book.setPrice(140.0);
    book.setStock(20);

    return Stream.of(Arguments.of(book));
  }
}
