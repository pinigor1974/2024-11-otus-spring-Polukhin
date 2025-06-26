package ru.otus.hw;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.BookService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@AutoConfigureMockMvc
@SpringBootTest(classes = {Application.class})
@Import(SecurityConfiguration.class)
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
        given(bookService.findAll()).willReturn(dbBooks.stream().map(BookDto::fromDomainObject).toList());

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
    public void testAuthenticatedOnBooksEndpoint() throws Exception {
        mvc.perform(get("/api/books")
                        .with(user("user").authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk());
    }


    @Test
    public void testAuthenticatedOnGetBookEndpoint() throws Exception {
        mvc.perform(get("/api/books/1")
                        .with(user("user").authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticatedOnDeleteBookEndpoint() throws Exception {
        mvc.perform(delete("/api/books/1")
                        .with(user("user").authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticatedOnPutBookEndpoint() throws Exception {
        mvc.perform(put("/api/books")
                        .with(user("user").authorities(new SimpleGrantedAuthority("ROLE_USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString( BookDto.builder().id(1).title("title1").build()))
                )
                .andExpect(status().isOk());
    }
}