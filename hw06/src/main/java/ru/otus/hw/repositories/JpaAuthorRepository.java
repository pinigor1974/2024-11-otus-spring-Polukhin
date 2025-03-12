package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> findAll() {
        return em.createNamedQuery("select * from Authors", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

}
