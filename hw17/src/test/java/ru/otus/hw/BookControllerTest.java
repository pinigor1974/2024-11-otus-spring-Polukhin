package ru.otus.hw;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.stream.IntStream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.services.BookService;
import static ru.otus.hw.rest.GlobalExceptionHandler.ERROR_STRING;


@AutoConfigureMockMvc
@SpringBootTest(classes = {Application.class})
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private BookService bookService;

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

    @Test
    void shouldReturnCorrectBooksList() throws Exception {
        given(bookService.findAll()).willReturn(dbBooks.stream().map(BookDto::fromDomainObject).toList());


        mvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookService.findAll())));
    }

    @Test
    void shouldReturnExpectedErrorWhenPersonsNotFound() throws Exception {
        given(bookService.findAll()).willReturn(List.of());

        mvc.perform(get("/api/books"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(ERROR_STRING));
    }

    @Test
    void shouldCorrectSaveNewPerson() throws Exception {
        BookDto book = BookDto.fromDomainObject(dbBooks.get(0));
        given(bookService.update(any(),any(), any(), any())).willReturn(book);
        String expectedResult = mapper.writeValueAsString(book);

        mvc.perform(put("/api/books").contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    void shouldCorrectDeleteNewPerson() throws Exception {
        String expectedResult = mapper.writeValueAsString(dbBooks.get(0));

        mvc.perform(delete("/api/books/" + dbBooks.get(0).getId()).contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk());
    }

}