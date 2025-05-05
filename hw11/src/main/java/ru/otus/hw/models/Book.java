package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import ru.otus.hw.dto.BookDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "books")

public class Book {

    @Id
    private Long id;

    private String title;

    private Long authorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return id == book.id && Objects.equals(authorId, book.authorId) && Objects.equals(title, book.title) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authorId);
    }

    public static Book fromBook(BookDto book) {
        return Book.builder().id(book.getId()).title(book.getTitle()).authorId(book.getAuthorId()).build();
    }
}
