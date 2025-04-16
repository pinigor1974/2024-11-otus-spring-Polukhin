package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.services.AuthorService;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final GenreService genreService;

    private final AuthorService authorService;

    @GetMapping("/api/books")
     public List<BookDto> listPage() {
        var books = bookService.findAll();
        return books;
    }

    @PostMapping("/api/books")
    public BookDto saveBook(@Valid @RequestBody BookDto book) {
        if (book.getId() > 0) {
            return bookService.update(book.getId(), book.getTitle(), book.getAuthorId(), book.getGenreIds());
        } else {
            return bookService.insert(book.getTitle(), book.getAuthorId(), book.getGenreIds());
        }
    }
}
