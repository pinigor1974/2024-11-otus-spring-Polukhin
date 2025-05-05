package ru.otus.hw.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "books_genres")
public class BookGenre {

    private Long bookId;

    private Long genreId;
}
