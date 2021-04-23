package com.bookstore.handler;

import com.bookstore.converter.BookConverter;
import com.bookstore.converter.BookListConverter;
import com.bookstore.model.entity.Book;
import com.bookstore.model.request.BookRequest;
import com.bookstore.model.response.BookListResponse;
import com.bookstore.model.response.BookResponse;
import com.bookstore.service.BookService;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookHandlerTest {

  @Mock
  private BookConverter bookConverter;

  @Mock
  private BookListConverter bookListConverter;

  @Mock
  private BookService bookService;

  @InjectMocks
  private BookHandler bookHandler;

  @Test
  public void it_should_find_all_books() {
    ArrayList<Book> bookList = new ArrayList<>();
    Mockito.when(bookService.findAll()).thenReturn(bookList);

    BookListResponse bookListResponse = new BookListResponse();
    Mockito.when(bookListConverter.convert(Mockito.any())).thenReturn(bookListResponse);

    bookHandler.findAllBooks();

    Mockito.verify(bookService).findAll();
    Mockito.verify(bookListConverter).convert(new ArrayList<>());
  }

  @ParameterizedTest
  @MethodSource("provideBook")
  public void it_should_find_book_by_id(BookRequest bookRequest, Book book, BookResponse bookResponse) {
    Mockito.when(bookService.findById(Mockito.anyInt())).thenReturn(book);
    Mockito.when(bookConverter.convert(Mockito.any(Book.class))).thenReturn(bookResponse);

    bookHandler.findBookById(1);

    Mockito.verify(bookService).findById(1);
    Mockito.verify(bookConverter).convert(book);
  }

  @ParameterizedTest
  @MethodSource("provideBook")
  public void it_should_create_book(BookRequest bookRequest, Book book, BookResponse bookResponse) {
    Mockito.when(bookConverter.convert(Mockito.any(BookRequest.class))).thenReturn(book);
    Mockito.when(bookConverter.convert(Mockito.any(Book.class))).thenReturn(bookResponse);
    Mockito.when(bookService.save(Mockito.any())).thenReturn(book);

    bookHandler.createBook(bookRequest);

    Mockito.verify(bookService).save(book);
    Mockito.verify(bookConverter).convert(book);
    Mockito.verify(bookConverter).convert(bookRequest);
  }

  @Test
  public void it_should_delete_book() {
    Book book = new Book();
    book.setId(1);
    Mockito.when(bookService.findById(Mockito.anyInt())).thenReturn(book);

    bookHandler.deleteBook(book.getId());

    Mockito.verify(bookService).findById(1);
  }

  private static Stream<Arguments> provideBook() {
    BookRequest bookRequest = new BookRequest();
    bookRequest.setDescription("Description of the book");
    bookRequest.setAuthor("Author of the book");
    bookRequest.setName("Name of the book");
    bookRequest.setStock(23);
    bookRequest.setPrice(44.5);

    Book book = new Book();
    book.setId(1);
    book.setDescription("Description of the book");
    book.setAuthor("Author of the book");
    book.setName("Name of the book");
    book.setStock(23);
    book.setPrice(44.5);

    BookResponse bookResponse = new BookResponse();
    bookResponse.setId(1);
    bookResponse.setDescription("Description of the book");
    bookResponse.setAuthor("Author of the book");
    bookResponse.setName("Name of the book");
    bookResponse.setStock(23);
    bookResponse.setPrice(44.5);

    return Stream.of(
        Arguments.of(bookRequest, book, bookResponse)
    );
  }
}