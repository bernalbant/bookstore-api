package com.bookstore.converter;

import com.bookstore.model.entity.Book;
import com.bookstore.model.response.BookListResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BookListConverter {

  private final BookConverter bookConverter;

  public BookListConverter(BookConverter bookConverter) {
    this.bookConverter = bookConverter;
  }

  public BookListResponse convert(List<Book> books) {
    BookListResponse bookListResponse = new BookListResponse();

    bookListResponse.setBookResponses(books
        .stream()
        .map(bookConverter::convert).collect(Collectors.toList()));

    return bookListResponse;
  }
}
