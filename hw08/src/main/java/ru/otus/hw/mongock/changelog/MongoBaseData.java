package ru.otus.hw.mongock.changelog;

import lombok.Getter;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;

@Getter
public class MongoBaseData {

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    private List<Book> dbBooks;

    private List<Comment> dbComments;


    public void setUp() {
        dbAuthors = generateDbAuthors();
        dbGenres = generateDbGenres();
        dbComments = generateDbComments();
        dbBooks = generateDbBooks(dbAuthors, dbGenres, dbComments);
    }


    protected static List<Author> generateDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    protected static List<Genre> generateDbGenres() {
        return IntStream.range(1, 7).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();

    }

    protected static List<Comment> generateDbComments() {
        return IntStream.range(1, 7).boxed()
                .map(id -> new Comment(id, "Comment_" + id))
                .toList();

    }

    protected static List<Book> generateDbBooks(List<Author> dbAuthors,
                                              List<Genre> dbGenres,
                                              List<Comment> dbComments) {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Book(String.valueOf(id),
                        "BookTitle_" + id,
                        dbAuthors.get(id - 1),
                        dbGenres.subList((id - 1) * 2, (id - 1) * 2 + 2),
                        dbComments.subList((id - 1) * 2, (id - 1) * 2 + 2)
                ))
                .toList();
    }


    protected void areBookTheSame(Book book1, Book book2) {
        assertThat(book1.getId()).isEqualTo(book2.getId());
        assertThat(book1.getId()).isEqualTo(book2.getId());
        assertThat(book1.getAuthor()).isEqualTo(book2.getAuthor());
        assertThat(book1.getGenres()).containsExactlyElementsOf(book2.getGenres());
        assertThat(book1.getComments()).containsExactlyElementsOf(book2.getComments());
    }

    protected void areListBookTheSame(List<Book> book1, List<Book> book2) {
        for (int i = 0; i < book1.size(); ++i) {
            areBookTheSame(book1.get(i), book2.get(i));
        }
    }
}
