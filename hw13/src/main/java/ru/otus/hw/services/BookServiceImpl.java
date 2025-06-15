package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.rest.NotFoundException;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @Transactional(readOnly = true)
    @Override
    public BookDto findById(long id) {
        var book = bookRepository.findById(id);
        book.ifPresent(b -> Hibernate.initialize(b.getComments()));
        if (id > 0 && book.isEmpty()) {
            throw new NotFoundException();
        }
        return book.map(BookDto::fromDomainObject).get();
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        var books = bookRepository.findAll();
        return books.stream().map(BookDto::fromDomainObject).toList();
    }

    @Transactional
    @Override
    public BookDto insert(String title, Long authorId, Set<Long> genresIds) {
        return BookDto.fromDomainObject(save(0, title, authorId, genresIds));
    }

    @Transactional
    @Override
    public BookDto update(Long id, String title, Long authorId, Set<Long> genresIds) {
        return BookDto.fromDomainObject(save(id, title, authorId, genresIds));
    }


    @Transactional
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    private Book save(long id, String title, long authorId, Set<Long> genresIds) {
        if (isEmpty(genresIds)) {
            throw new IllegalArgumentException("Genres ids must not be null");
        }

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genres = genreRepository.findAllByIds(genresIds);
        if (isEmpty(genres) || genresIds.size() != genres.size()) {
            throw new EntityNotFoundException("One or all genres with ids %s not found".formatted(genresIds));
        }

        var book = new Book(id, title, author, genres, Collections.emptyList());
        return bookRepository.save(book);
    }
}
