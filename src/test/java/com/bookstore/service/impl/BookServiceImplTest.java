package com.bookstore.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookstore.exception.NotFoundException;
import com.bookstore.model.entity.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.BookServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

  @Mock
  private BookRepository bookRepository;

  @InjectMocks
  private BookServiceImpl bookService;

  @ParameterizedTest
  @MethodSource("provideBooks")
  public void it_should_find_all_books(Book book) {
    List<Book> bookList = List.of(book);

    Mockito.when(bookRepository.findAll()).thenReturn(bookList);

    List<Book> allBooks = bookService.findAll();

    Mockito.verify(bookRepository).findAll();
    assertThat(allBooks).isNotNull();
    assertThat(allBooks.get(0).getStock()).isEqualTo(book.getStock());
    assertThat(allBooks.get(0).getPrice()).isEqualTo(book.getPrice());
    assertThat(allBooks.get(0).getId()).isEqualTo(book.getId());
    assertThat(allBooks.get(0).getAuthor()).isEqualTo(book.getAuthor());
    assertThat(allBooks.get(0).getName()).isEqualTo(book.getName());
    assertThat(allBooks.get(0).getDescription()).isEqualTo(book.getDescription());
  }

  @ParameterizedTest
  @MethodSource("provideBooks")
  public void it_should_find_book_by_id(Book book) {
    Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(book));

    Book foundBook = bookService.findById(book.getId());

    Mockito.verify(bookRepository).findById(book.getId());
    assertThat(foundBook).isNotNull();
    assertThat(foundBook.getStock()).isEqualTo(book.getStock());
    assertThat(foundBook.getPrice()).isEqualTo(book.getPrice());
    assertThat(foundBook.getId()).isEqualTo(book.getId());
    assertThat(foundBook.getAuthor()).isEqualTo(book.getAuthor());
    assertThat(foundBook.getName()).isEqualTo(book.getName());
    assertThat(foundBook.getDescription()).isEqualTo(book.getDescription());
  }

  @ParameterizedTest
  @MethodSource("provideExceptionBooks")
  public void it_should_throw_not_found_exception_when_there_are_no_any_book(Book book) {
    Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

    NotFoundException notFoundException = Assertions.catchThrowableOfType(() ->
            bookService.findById(book.getId()),
        NotFoundException.class);

    Mockito.verify(bookRepository).findById(book.getId());
    assertThat(notFoundException).isNotNull();
    assertThat(notFoundException.getMessage()).isEqualTo("Book not found with the given id " + book.getId());
  }

  @ParameterizedTest
  @MethodSource("provideBooks")
  public void it_should_save_book(Book book) {
    Mockito.when(bookRepository.save(Mockito.any())).thenReturn(book);

    Book savedBook = bookService.save(book);

    Mockito.verify(bookRepository).save(book);
    assertThat(savedBook).isNotNull();
    assertThat(savedBook.getStock()).isEqualTo(book.getStock());
    assertThat(savedBook.getPrice()).isEqualTo(book.getPrice());
    assertThat(savedBook.getId()).isEqualTo(book.getId());
    assertThat(savedBook.getAuthor()).isEqualTo(book.getAuthor());
    assertThat(savedBook.getName()).isEqualTo(book.getName());
    assertThat(savedBook.getDescription()).isEqualTo(book.getDescription());
  }

  private static Stream<Arguments> provideExceptionBooks() {
    Book book = new Book();
    book.setId(1);
    book.setDescription("Description of the book");
    book.setAuthor("Author of the book");
    book.setName("Name of the book");
    book.setStock(23);
    book.setPrice(44.5);

    return Stream.of(
        Arguments.of(book)
    );
  }

  private static Stream<Arguments> provideBooks() {
    Book book = new Book();
    book.setId(1);
    book.setDescription("Description of the book");
    book.setAuthor("Author of the book");
    book.setName("Name of the book");
    book.setStock(23);
    book.setPrice(44.5);

    Book book1 = new Book();
    book1.setId(2);
    book1.setStock(9);
    book1.setDescription("Description of the book");
    book1.setAuthor("Author of the book");
    book1.setName("Name of the book");
    book1.setPrice(14.5);

    Book book2 = new Book();
    book1.setId(22);
    book1.setStock(19);
    book1.setDescription("Description of the book");
    book1.setAuthor("Author of the book");
    book1.setName("Name of the book");
    book1.setPrice(55.5);

    return Stream.of(
        Arguments.of(book),
        Arguments.of(book1),
        Arguments.of(book2)
    );
  }
}
