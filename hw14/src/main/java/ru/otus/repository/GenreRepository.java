package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre,Long> {

    Genre save(Genre domain);

    List<Genre> findAll();

    Optional<Genre> findById(@Param("id") Long id);

    List<Genre> findByName(@Param("name") String name);

    void deleteById(@Param("id") Long id);
}
