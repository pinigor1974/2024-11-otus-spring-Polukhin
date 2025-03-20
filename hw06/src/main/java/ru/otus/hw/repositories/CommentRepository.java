package ru.otus.hw.repositories;

import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment>  findAll(Book book);

    Optional<Comment> findById(long id);
}
