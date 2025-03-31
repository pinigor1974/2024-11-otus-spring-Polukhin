package ru.otus.hw;

import org.junit.jupiter.api.BeforeEach;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


public class JpaBaseTest {



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

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        var dbComments = getDbComments();
        return getDbBooks(dbAuthors, dbGenres, dbComments);
    }

    protected void areBookTheSame(Book book1, Book book2){
        assertThat(book1.getId()).isEqualTo(book2.getId()) ;
        assertThat(book1.getId()).isEqualTo(book2.getId());
        assertThat(book1.getAuthor()).isEqualTo(book2.getAuthor());
        assertThat(book1.getGenres()).containsExactlyElementsOf(book2.getGenres());
        assertThat(book1.getComments()).containsExactlyElementsOf(book2.getComments());
    }
    protected void areListBookTheSame(List<Book> book1, List<Book> book2){
        for (int i = 0; i < book1.size(); ++i){
            areBookTheSame(book1.get(i), book2.get(i));
        }
    }
}
