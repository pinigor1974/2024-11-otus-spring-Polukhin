package ru.otus.hw.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.BookGenre;

public interface BookGenreRepository extends ReactiveCrudRepository<BookGenre, Long> {

    Flux<BookGenre> findByBookId(long bookId);

    Mono<Void> deleteByBookId(long bookId);
}
