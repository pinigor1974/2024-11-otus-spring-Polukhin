package ru.otus.hw;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controllers.BookController;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.controllers.NotFoundException;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;

@AutoConfigureMockMvc
@SpringBootTest(classes = {Application.class})
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    protected List<Author> dbAuthors;

    protected List<Genre> dbGenres;

    protected List<Book> dbBooks;

    protected List<Comment> dbComments;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbComments = getDbComments();
        dbBooks = getDbBooks(dbAuthors, dbGenres, dbComments);

    }

    @MockitoBean
    private BookRepository bookRepository;


    @Test
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        when(bookRepository.findAll()).thenReturn(dbBooks);
        List<Book> expectedBooks = dbBooks;
        mvc.perform(get("/"))
                .andExpect(view().name("list"))
                .andExpect(model().attribute("books", expectedBooks));
    }

    @Test
    void shouldRenderEditPageWithCorrectViewAndModelAttributes() throws Exception {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(dbBooks.get(0)));
        BookDto expectedBook = BookDto.fromDomainObject(Optional.of(dbBooks.get(0)));
        mvc.perform(get("/edit").param("id", "1"))
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("book", expectedBook));
    }

    @Test
    void shouldRenderErrorPageWhenPersonNotFound() throws Exception {
        when(bookRepository.findById(1L)).thenThrow(new NotFoundException());
        mvc.perform(get("/edit").param("id", "1"))
                .andExpect(view().name("customError"));
    }

    @Test
    void shouldSavePersonAndRedirectToContextPath() throws Exception {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(dbBooks.get(0)));
        mvc.perform(post("/edit")
                        .param("id", "3")
                        .param("genres", "1","2")
                        .param("author", "1")
                        .param("title", "hello title")
                )
                .andExpect(view().name("redirect:/"));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldDeleteBookAndRedirectToContextPath() throws Exception {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(dbBooks.get(0)));
        mvc.perform(get("/delete")
                        .param("id", "1")
                )
                .andExpect(view().name("redirect:/"));
        verify(bookRepository, times(1)).deleteById(1L);
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 7).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();

    }

    private static List<Comment> getDbComments() {
        return IntStream.range(1, 7).boxed()
                .map(id -> new Comment(id, "Comment_" + id))
                .toList();

    }

    private static List<Book> getDbBooks(List<Author> dbAuthors,
                                         List<Genre> dbGenres,
                                         List<Comment> dbComments) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(id,
                        "BookTitle_" + id,
                        dbAuthors.get(id - 1),
                        dbGenres.subList((id - 1) * 2, (id - 1) * 2 + 2),
                        dbComments.subList((id - 1) * 2, (id - 1) * 2 + 2)
                ))
                .toList();
    }
}