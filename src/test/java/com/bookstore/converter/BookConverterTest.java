package com.bookstore.converter;

import com.bookstore.model.entity.Book;
import com.bookstore.model.request.BookRequest;
import com.bookstore.model.response.BookResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BookConverterTest {

  @Autowired
  private BookConverter bookConverter;


  @BeforeEach
  void init() {
    bookConverter = new BookConverter();
  }

  @Test
  public void itShouldConvertBookToBookResponse() {
    Book book = new Book();
    book.setId(1);
    book.setDescription("Description of the book");
    book.setAuthor("Author of the book");
    book.setName("Name of the book");
    book.setStock(23);
    book.setPrice(44.5);

    BookResponse bookResponse = bookConverter.convert(book);

    Assertions.assertThat(bookResponse).isNotNull();
    Assertions.assertThat(bookResponse.getId()).isEqualTo(1);
    Assertions.assertThat(bookResponse.getName()).isEqualTo("Name of the book");
    Assertions.assertThat(bookResponse.getDescription()).isEqualTo("Description of the book");
    Assertions.assertThat(bookResponse.getAuthor()).isEqualTo("Author of the book");
    Assertions.assertThat(bookResponse.getPrice()).isEqualTo(44.5);
    Assertions.assertThat(bookResponse.getStock()).isEqualTo(23);
  }

  @Test
  public void itShouldConvertBookRequestToBook() {
    BookRequest bookRequest = new BookRequest();
    bookRequest.setDescription("Description of the book");
    bookRequest.setAuthor("Author of the book");
    bookRequest.setName("Name of the book");
    bookRequest.setStock(23);
    bookRequest.setPrice(44.5);

    Book book = bookConverter.convert(bookRequest);

    Assertions.assertThat(book).isNotNull();
    Assertions.assertThat(book.getName()).isEqualTo("Name of the book");
    Assertions.assertThat(book.getDescription()).isEqualTo("Description of the book");
    Assertions.assertThat(book.getAuthor()).isEqualTo("Author of the book");
    Assertions.assertThat(book.getPrice()).isEqualTo(44.5);
    Assertions.assertThat(book.getStock()).isEqualTo(23);
  }
}
