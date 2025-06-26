package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.services.BookService;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books")
    public List<BookDto> listPage() {
        var books = bookService.findAll();
        if (books.isEmpty()) {
            throw new NotFoundException();
        }
        return books;
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable("id") Long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/api/books")
    public ResponseEntity<BookDto> saveBook(@RequestBody BookDto book) {
        if (book.getId() > 0) {
            return ResponseEntity
                    .ok(bookService.update(book.getId(), book.getTitle(), book.getAuthorId(), book.getGenreIds()));
        } else {
            return ResponseEntity
                    .ok(bookService.insert(book.getTitle(), book.getAuthorId(), book.getGenreIds()));
        }
    }
}
