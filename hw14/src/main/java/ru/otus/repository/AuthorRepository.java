package ru.otus.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Author;
import java.util.List;
import java.util.Optional;


public interface AuthorRepository extends PagingAndSortingRepository<Author,Long> {

    Author save(Author domain);

    List<Author> findAll();

    Optional<Author> findById(Long id);

    @Query("select a from Author a where a.name = :name")
    List<Author> findByName(@Param("name") String name);

    void deleteById(@Param("id") Long id);

    @Modifying
    @Query("update Author a set a.name = :name where a.id = :id")
    void updateNameById(@Param("id") long id, @Param("name") String name);
}
