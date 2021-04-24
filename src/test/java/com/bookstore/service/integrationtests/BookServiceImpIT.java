package com.bookstore.service.integrationtests;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookstore.exception.NotFoundException;
import com.bookstore.model.entity.Book;
import com.bookstore.service.BookService;
import com.bookstore.service.integrationtests.base.MongoDbBaseContainer;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceImpIT extends MongoDbBaseContainer {

  @Autowired
  private BookService bookService;

  @BeforeEach
  public void setUp() {
    bookService.save(new Book(1, "Name of the book", "Description of the book", "Author of the book", 12.0, 2));
    bookService.save(new Book(2, "Name of the second book", "Description of the second book", "Author of the second book", 22.0, 12));
    bookService.save(new Book(3, "Name of the third book", "Description of the third book", "Author of the third book", 44.0, 23));
  }

  @Test
  @Order(1)
  public void itShouldFindSavedFirstBook() {
    var book = bookService.findById(1);

    assertThat(book.getId()).isEqualTo(1);
    assertThat(book.getDescription()).isEqualTo("Description of the book");
    assertThat(book.getName()).isEqualTo("Name of the book");
    assertThat(book.getAuthor()).isEqualTo("Author of the book");
    assertThat(book.getPrice()).isEqualTo(12.0);
    assertThat(book.getStock()).isEqualTo(2);
  }

  @Test
  @Order(1)
  public void itShouldFindSavedSecondBook() {
    var book = bookService.findById(2);

    assertThat(book.getId()).isEqualTo(2);
    assertThat(book.getDescription()).isEqualTo("Description of the second book");
    assertThat(book.getName()).isEqualTo("Name of the second book");
    assertThat(book.getAuthor()).isEqualTo("Author of the second book");
    assertThat(book.getPrice()).isEqualTo(22.0);
    assertThat(book.getStock()).isEqualTo(12);
  }

  @Test
  @Order(1)
  public void itShouldFindSavedThirdBook() {
    var book = bookService.findById(3);

    assertThat(book.getId()).isEqualTo(3);
    assertThat(book.getDescription()).isEqualTo("Description of the third book");
    assertThat(book.getName()).isEqualTo("Name of the third book");
    assertThat(book.getAuthor()).isEqualTo("Author of the third book");
    assertThat(book.getPrice()).isEqualTo(44.0);
    assertThat(book.getStock()).isEqualTo(23);
  }

  @Test
  @Order(2)
  public void itShouldThrowExceptionWhenThereIsNoBookWithSpecifiedId() {
    int bookId = 4;
    var notFoundException = Assertions.catchThrowableOfType(() ->
            bookService.findById(bookId),
        NotFoundException.class
    );

    assertThat(notFoundException).isNotNull();
    assertThat(notFoundException.getMessage()).isEqualTo("Book not found with the given id " + bookId);
  }

  @Test
  @Order(3)
  public void itShouldFindAllSavedBooks() {
    var allBooks = bookService.findAll();

    assertThat(allBooks.size()).isEqualTo(3);

    Book book = allBooks.get(0);
    assertThat(book.getId()).isEqualTo(1);
    assertThat(book.getDescription()).isEqualTo("Description of the book");
    assertThat(book.getName()).isEqualTo("Name of the book");
    assertThat(book.getAuthor()).isEqualTo("Author of the book");
    assertThat(book.getPrice()).isEqualTo(12.0);
    assertThat(book.getStock()).isEqualTo(2);

    Book secondBook = allBooks.get(1);
    assertThat(secondBook.getId()).isEqualTo(2);
    assertThat(secondBook.getDescription()).isEqualTo("Description of the second book");
    assertThat(secondBook.getName()).isEqualTo("Name of the second book");
    assertThat(secondBook.getAuthor()).isEqualTo("Author of the second book");
    assertThat(secondBook.getPrice()).isEqualTo(22.0);
    assertThat(secondBook.getStock()).isEqualTo(12);

    Book thirdBook = allBooks.get(2);
    assertThat(thirdBook.getId()).isEqualTo(3);
    assertThat(thirdBook.getDescription()).isEqualTo("Description of the third book");
    assertThat(thirdBook.getName()).isEqualTo("Name of the third book");
    assertThat(thirdBook.getAuthor()).isEqualTo("Author of the third book");
    assertThat(thirdBook.getPrice()).isEqualTo(44.0);
    assertThat(thirdBook.getStock()).isEqualTo(23);
  }

  @Test
  @Order(4)
  public void itShouldFindBooksAfterOneBookDeleted() {
    bookService.deleteById(1);

    List<Book> booksAfterDeletion = bookService.findAll();

    assertThat(booksAfterDeletion.size()).isEqualTo(2);

    Book secondBook = booksAfterDeletion.get(0);
    assertThat(secondBook.getId()).isEqualTo(2);
    assertThat(secondBook.getDescription()).isEqualTo("Description of the second book");
    assertThat(secondBook.getName()).isEqualTo("Name of the second book");
    assertThat(secondBook.getAuthor()).isEqualTo("Author of the second book");
    assertThat(secondBook.getPrice()).isEqualTo(22.0);
    assertThat(secondBook.getStock()).isEqualTo(12);

    Book thirdBook = booksAfterDeletion.get(1);
    assertThat(thirdBook.getId()).isEqualTo(3);
    assertThat(thirdBook.getDescription()).isEqualTo("Description of the third book");
    assertThat(thirdBook.getName()).isEqualTo("Name of the third book");
    assertThat(thirdBook.getAuthor()).isEqualTo("Author of the third book");
    assertThat(thirdBook.getPrice()).isEqualTo(44.0);
    assertThat(thirdBook.getStock()).isEqualTo(23);
  }
}
