package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import ru.otus.hw.dto.BookDto;

import ru.otus.hw.models.Book;
import ru.otus.hw.models.BookGenre;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.stream.Collectors;

import ru.otus.hw.repositories.BookGenreRepository;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookGenreRepository bookGenreRepository;

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final Scheduler workerPool;

    @Transactional(readOnly = true)
    @Override
    public Mono<BookDto> findById(long id) {
        return id > 0 ? bookRepository
                .findById(id)
                .flatMap(b ->
                        authorRepository
                                .findById(b.getAuthorId())
                                .flatMap(a -> bookGenreRepository.findByBookId(b.getId()).collectList()
                                        .flatMap(
                                                bg -> genreRepository
                                                        .findAllById(bg.stream().map(
                                                                BookGenre::getGenreId).toList()).collectList())
                                        .map(
                                                genres ->
                                                        BookDto
                                                                .builder()
                                                                .id(b.getId())
                                                                .title(b.getTitle())
                                                                .authorId(a.getId()).authorFullName(a.getFullName())
                                                                .genreIds(genres.stream().map(Genre::getId)
                                                                        .collect(Collectors.toSet()))
                                                                .genreNames(genres.stream()
                                                                        .map(Genre::getName).toList())
                                                                .build())))
                : Mono.just(BookDto.builder().build());
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<BookDto> findAll() {

        return bookRepository.findAll().flatMap(b ->
                authorRepository
                        .findById(b.getAuthorId())
                        .flatMap(a -> bookGenreRepository.findByBookId(b.getId()).collectList()
                                .flatMap(bg -> genreRepository.findAllById(bg.stream().map(
                                        BookGenre::getGenreId).toList()).collectList())
                                .flatMap(genres -> commentRepository.findByBookId(b.getId()).collectList()
                                        .map(comments ->
                                                BookDto
                                                        .builder()
                                                        .id(b.getId())
                                                        .title(b.getTitle()).authorId(a.getId())
                                                        .authorFullName(a.getFullName())
                                                        .genreNames(genres.stream().map(Genre::getName).toList())
                                                        .commentNames(comments.stream().map(Comment::getData).toList())
                                                        .build()))));
    }

    @Transactional
    @Override
    public Mono<Void> deleteById(Long id) {

        return bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Mono<BookDto> save(BookDto book) {
        return
                bookRepository.save(Book.fromBook(book)).flatMap(b ->
                        bookGenreRepository.deleteByBookId(b.getId()).then(
                                bookGenreRepository.saveAll(
                                        book
                                                .getGenreIds()
                                                .stream()
                                                .map(gId -> BookGenre.builder().genreId(gId).bookId(b.getId()).build())
                                                .toList()
                                ).collectList()).flatMap(v2 -> findById(b.getId())));
    }
}
