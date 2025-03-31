package ru.otus.hw;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Репозиторий на основе Jdbc для работы с книгами ")
@DataJpaTest(properties = {"spring.shell.interactive.enabled=false"})
@ComponentScan()
class JpaBookServiceTest extends JpaBaseTest {

    @Autowired
    private BookService bookService;

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = bookService.findAll();
        var expectedBooks = dbBooks;
        areListBookTheSame(actualBooks, expectedBooks);
        actualBooks.forEach(System.out::println);
    }


    @DisplayName("должен загружать книгу по id")
    @ParameterizedTest
    @MethodSource("getDbBooks")
    void shouldReturnCorrectBookById(Book expectedBook) {
        var actualBook = bookService.findById(expectedBook.getId());
        areBookTheSame(actualBook.get(), expectedBook);
    }



    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book(4, "BookTitle_10500", dbAuthors.get(0),
                List.of(dbGenres.get(0), dbGenres.get(2)), List.of());
        var returnedBook = bookService.insert(
                expectedBook.getTitle(),
                expectedBook.getAuthor().getId(),
                expectedBook.getGenres().stream().map(Genre::getId).collect(Collectors.toSet())
        );
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookService.findById(returnedBook.getId()))
                .isPresent()
                .isEqualTo(Optional.of(returnedBook));
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book(1L, "BookTitle_10500", dbAuthors.get(2),
                List.of(dbGenres.get(4), dbGenres.get(5)), List.of());

        assertThat(bookService.findById(expectedBook.getId()))
                .isPresent()
                .isNotEqualTo(Optional.of(expectedBook));

        var returnedBook = bookService.update(
                expectedBook.getId(),
                expectedBook.getTitle(),
                expectedBook.getAuthor().getId(),
                expectedBook.getGenres().stream().map(g->g.getId()).collect(Collectors.toSet()));
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(bookService.findById(returnedBook.getId()))
                .isPresent()
                .isEqualTo(Optional.of(returnedBook));
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertThat(bookService.findById(1L)).isPresent();
        bookService.deleteById(1L);
        assertThat(bookService.findById(1L)).isEmpty();
    }

    @DisplayName("должен не сохранить книгу по id ")
    @Test
    void shouldNotFindBook() {
        assertThrows(EntityNotFoundException.class, () -> {
            bookService.update(1L, "unknown", -1L, Set.of(-1L, -2L)
            );
        });
    }


}