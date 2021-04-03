package com.bookstore.handler;

import com.bookstore.converter.BookConverter;
import com.bookstore.converter.BookListConverter;
import com.bookstore.model.entity.Book;
import com.bookstore.model.request.BookRequest;
import com.bookstore.model.response.BookListResponse;
import com.bookstore.model.response.BookResponse;
import com.bookstore.service.BookService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BookHandler {

  private BookConverter bookConverter;
  private BookListConverter bookListConverter;
  private BookService bookService;

  public BookHandler(BookConverter bookConverter, BookService bookService, BookListConverter bookListConverter) {
    this.bookConverter = bookConverter;
    this.bookListConverter = bookListConverter;
    this.bookService = bookService;
  }

  public BookListResponse findAllBooks() {
    List<Book> bookList = bookService.findAll();
    return bookListConverter.convert(bookList);
  }

  public BookResponse findBookById(int id) {
    Book book = bookService.findById(id);
    return bookConverter.convert(book);
  }

  public BookResponse createBook(BookRequest bookRequest) {
    Book savedBook = bookService.save(bookConverter.convert(bookRequest));
    return bookConverter.convert(savedBook);
  }

  public void deleteBook(int id) {
    Book book = bookService.findById(id);
    bookService.deleteById(book.getId());
  }
}
