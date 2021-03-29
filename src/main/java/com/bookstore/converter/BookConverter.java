package com.bookstore.converter;

import com.bookstore.model.entity.Book;
import com.bookstore.model.request.BookRequest;
import com.bookstore.model.response.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

  public BookResponse convert(Book book) {
    BookResponse bookResponse = new BookResponse();
    bookResponse.setId(book.getId());
    bookResponse.setName(book.getName());
    bookResponse.setDescription(book.getDescription());
    bookResponse.setAuthor(book.getAuthor());
    bookResponse.setPrice(book.getPrice());
    bookResponse.setStock(book.getStock());

    return bookResponse;
  }

  public Book convert(BookRequest bookRequest) {
    Book book = new Book();
    book.setName(bookRequest.getName());
    book.setDescription(bookRequest.getDescription());
    book.setAuthor(bookRequest.getAuthor());
    book.setPrice(bookRequest.getPrice());
    book.setStock(bookRequest.getStock());

    return book;
  }
}
