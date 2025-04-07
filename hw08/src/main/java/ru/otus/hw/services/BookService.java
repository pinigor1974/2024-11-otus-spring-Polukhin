package ru.otus.hw.services;

import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Optional<Book> findById(String id);

    List<Book> findAll();

    Book insert(String title, long authorId, Set<Long> genresIds);

    Book update(String id, String title, long authorId, Set<Long> genresIds);

    void deleteById(String id);
}
