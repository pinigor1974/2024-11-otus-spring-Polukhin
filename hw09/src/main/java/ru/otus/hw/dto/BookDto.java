package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BookDto {
    private long id;

    @NotBlank(message = "{title-field-should-not-be-blank}")
    @Size(min = 2, max = 100, message = "{title-field-should-has-expected-size}")
    private String title;

    @Positive
    private Long author = 0L;

    @NotEmpty
    private Set<Long> genres = Collections.emptySet();


    public static BookDto fromDomainObject(Optional<Book> book) {
        return book.map(b -> new BookDto(
                b.getId(),
                b.getTitle(),
                b.getAuthor().getId(),
                b.getGenres().stream().map(Genre::getId).collect(Collectors.toSet())
                )).orElse(new BookDto());
    }
}

