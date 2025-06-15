package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class BookDto {
    @Id
    private long id;

    @NotBlank(message = "{title-field-should-not-be-blank}")
    @Size(min = 2, max = 100, message = "{title-field-should-has-expected-size}")
    private String title;


    private String authorFullName;

    private Long authorId;

    @NotEmpty
    private Set<Long> genreIds  = Collections.emptySet();

    private List<String> genreNames = Collections.emptyList();

    private List<String> commentNames = Collections.emptyList();

    public static BookDto fromDomainObject(Book  b) {
        return BookDto
                .builder()
                .id(b.getId())
                .authorId(b.getAuthor().getId())
                .authorFullName(b.getAuthor().getFullName())
                .genreIds(b.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()))
                .genreNames(b.getGenres().stream().map(Genre::getName).toList())
                .commentNames(b.getComments().stream().map(Comment::getData).toList())
                .title(b.getTitle())
                .build();
    }

    public static BookDto fromDO(Optional<Book>  book) {
        return book.map(BookDto::fromDomainObject).orElse(new BookDto());
    }
}

