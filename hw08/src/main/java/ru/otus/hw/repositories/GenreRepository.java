package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends MongoRepository<Genre, Long> {
    default List<Genre> findAllByIds(Set<Long> ids){
        return this.findAllById(ids);
    }
}
