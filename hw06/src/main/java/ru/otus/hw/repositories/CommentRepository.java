package ru.otus.hw.repositories;

import ru.otus.hw.models.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment>  findAll(long bookId);

    Optional<Comment> findById(long id);
}
