package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    @Override
    public List<Comment> findAll(long bookId) {
        return bookRepository.findById(String.valueOf(bookId)).orElseThrow().getComments();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return bookRepository
                .findAll()
                .stream()
                .flatMap(b -> b.getComments().stream()).filter(c -> c.getId() == id)
                .findAny();
    }
}
