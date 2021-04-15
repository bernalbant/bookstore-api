package com.bookstore.converter;

import com.bookstore.model.entity.Book;
import com.bookstore.model.response.BookListResponse;
import com.bookstore.model.response.BookResponse;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookListConverterTest {

  @InjectMocks
  private BookListConverter bookListConverter;

  @Mock
  private BookConverter bookConverter;

  @Test
  public void itShouldConvertBookListToBookListResponse() {
    Book book = new Book();
    book.setId(1);
    book.setDescription("Description of the book");
    book.setAuthor("Author of the book");
    book.setName("Name of the book");
    book.setStock(23);
    book.setPrice(44.5);

    BookResponse bookResponse = new BookResponse();
    bookResponse.setStock(23);
    bookResponse.setId(1);
    bookResponse.setDescription("Description of the book");
    bookResponse.setAuthor("Author of the book");
    bookResponse.setName("Name of the book");
    bookResponse.setStock(23);
    bookResponse.setPrice(44.5);

    Mockito.when(bookConverter.convert(Mockito.any(Book.class))).thenReturn(bookResponse);

    BookListResponse conversionOfBookList = bookListConverter.convert(List.of(book));

    Assertions.assertThat(conversionOfBookList).isNotNull();
    Mockito.verify(bookConverter).convert(book);

    BookResponse bookResponseList = conversionOfBookList.getBookResponses().get(0);
    Assertions.assertThat(bookResponseList.getId()).isEqualTo(book.getId());
    Assertions.assertThat(bookResponseList.getStock()).isEqualTo(book.getStock());
    Assertions.assertThat(bookResponseList.getPrice()).isEqualTo(book.getPrice());
    Assertions.assertThat(bookResponseList.getAuthor()).isEqualTo(book.getAuthor());
    Assertions.assertThat(bookResponseList.getDescription()).isEqualTo(book.getDescription());
    Assertions.assertThat(bookResponseList.getName()).isEqualTo(book.getName());
  }
}
