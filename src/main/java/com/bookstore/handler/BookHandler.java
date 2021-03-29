package com.bookstore.handler;

import com.bookstore.model.entity.Book;
import com.bookstore.model.request.BookRequest;
import com.bookstore.model.response.BookListResponse;
import com.bookstore.model.response.BookResponse;
import com.bookstore.service.BookService;
import org.springframework.stereotype.Component;

@Component
public class BookHandler {

  private BookService bookService;

  public BookHandler(BookService bookService) {
    this.bookService = bookService;
  }

  public BookListResponse findAllBooks() {
    return null;
  }

  public BookResponse findBookById(int id) {
    return null;
  }

  public BookResponse createBook(BookRequest bookRequest) {
    return null;
  }

  public void deleteBook(int id) {
    Book book = bookService.findById(id);
    bookService.deleteById(book.getId());
  }
}
