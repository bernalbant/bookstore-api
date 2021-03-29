package com.bookstore.controller;

import com.bookstore.handler.BookHandler;
import com.bookstore.model.request.BookRequest;
import com.bookstore.model.response.BookListResponse;
import com.bookstore.model.response.BookResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

  private BookHandler bookHandler;

  public BookController(BookHandler bookHandler) {
    this.bookHandler = bookHandler;
  }

  @GetMapping
  public BookListResponse findAllBooks() {
    return bookHandler.findAllBooks();
  }

  @GetMapping("/{id}")
  public BookResponse findBookById(@PathVariable int id) {
    return bookHandler.findBookById(id);
  }

  @PostMapping
  public BookResponse createBook(@Valid @RequestBody BookRequest bookRequest) {
    return bookHandler.createBook(bookRequest);
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable int id) {
    bookHandler.deleteBook(id);
  }
}