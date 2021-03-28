package com.bookstore.service;

import com.bookstore.model.entity.Book;
import com.bookstore.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  private BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @Override
  public Book findById(int id) {
    Optional<Book> book = bookRepository.findById(id);

    if (book.isPresent()) {
      return book.get();
    } else {
      throw new RuntimeException();
    }
  }

  @Override
  public Book save(Book book) {
    return bookRepository.save(book);
  }

  @Override
  public void deleteById(int id) {
    bookRepository.deleteById(id);
  }
}