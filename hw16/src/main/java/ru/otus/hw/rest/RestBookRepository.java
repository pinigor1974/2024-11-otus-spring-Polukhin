package ru.otus.hw.rest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.hw.models.Book;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface RestBookRepository  extends JpaRepository<Book, Long> {
}
