package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends MongoRepository<Author, Long> {
}
