package ru.otus.hw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.mongock.changelog.MongoBaseData;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Репозиторий на основе mongo для работы с книгами ")
@SpringBootTest(
        classes = {Application.class},
        properties = {"spring.shell.interactive.enabled=false"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceTest extends MongoBaseData {

    @Autowired
    private BookService bookService;
    @BeforeEach
    public void setUp() {
        super.setUp();
    }




    @DisplayName("должен сохранять новую книгу")
    @Test
    @Order(2)
    void shouldSaveNewBook() {
        System.out.println("save new book!!!!!!!!!!!!!!!");
        var expectedBook = new Book(null, "BookTitle_10500", this.getDbAuthors().get(0),
                List.of(this.getDbGenres().get(0), this.getDbGenres().get(2)), List.of());
        var returnedBook = bookService.insert(
                expectedBook.getTitle(),
                expectedBook.getAuthor().getId(),
                expectedBook.getGenres().stream().map(Genre::getId).collect(Collectors.toSet())
        );
        assertThat(returnedBook).isNotNull()
                .matches(book -> Optional.ofNullable(book.getId()).isPresent())
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookService.findById(returnedBook.getId()))
                .isPresent()
                .isEqualTo(Optional.of(returnedBook));
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    @Order(3)
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book(String.valueOf(1L), "BookTitle_10500", this.getDbAuthors().get(2),
                List.of(this.getDbGenres().get(4), this.getDbGenres().get(5)), List.of());

        assertThat(bookService.findById(expectedBook.getId()))
                .isPresent()
                .isNotEqualTo(Optional.of(expectedBook));

        var returnedBook = bookService.update(
                expectedBook.getId(),
                expectedBook.getTitle(),
                expectedBook.getAuthor().getId(),
                expectedBook.getGenres().stream().map(g->g.getId()).collect(Collectors.toSet()));
        assertThat(returnedBook).isNotNull()
                .matches(book -> Long.parseLong(book.getId() )> 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookService.findById(returnedBook.getId()))
                .isPresent()
                .isEqualTo(Optional.of(returnedBook));
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    @Order(4)
    void shouldDeleteBook() {
        assertThat(bookService.findById(String.valueOf(1L))).isPresent();
        bookService.deleteById(String.valueOf(1L));
        assertThat(bookService.findById(String.valueOf(1L))).isEmpty();
    }

    @DisplayName("должен не сохранить книгу по id ")
    @Test
    @Order(5)
    void shouldNotFindBook() {
        assertThrows(EntityNotFoundException.class, () -> {
            bookService.update(String.valueOf(1L), "unknown", -1L, Set.of(-1L, -2L)
            );
        });
    }
    @DisplayName("должен загружать список всех книг")
    @Test
    @Order(1)
    void shouldReturnCorrectBooksList() {
        var actualBooks = bookService.findAll();
        var expectedBooks = this.getDbBooks();
        areListBookTheSame(actualBooks, expectedBooks);
        actualBooks.forEach(System.out::println);
    }
    private static List<Book> generatedDbBooks() {
        var dbAuthors = generateDbAuthors();
        var dbGenres = generateDbGenres();
        var dbComments = generateDbComments();
        return generateDbBooks(dbAuthors, dbGenres, dbComments);
    }
}