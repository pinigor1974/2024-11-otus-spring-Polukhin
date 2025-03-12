package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;

import static org.hibernate.jpa.QueryHints.JAKARTA_HINT_FETCH_GRAPH;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(
                em.find(
                        Book.class,
                        id,
                        Map.of(JAKARTA_HINT_FETCH_GRAPH, em.getEntityGraph("books"))
                )
        );
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery(" from Book", Book.class)
                .setHint(JAKARTA_HINT_FETCH_GRAPH, em.getEntityGraph("books"))
                .getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() < 0) {
            throw new EntityNotFoundException("book not found : " + book.getId());
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        em.remove(em.getReference(Book.class, id));
    }
}
