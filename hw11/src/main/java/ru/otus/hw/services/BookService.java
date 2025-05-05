package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookDto;

public interface BookService {
    Mono<BookDto> findById(long id);

    Flux<BookDto> findAll();


    Mono<Void> deleteById(Long id);


    Mono<BookDto> save(BookDto book);
}
