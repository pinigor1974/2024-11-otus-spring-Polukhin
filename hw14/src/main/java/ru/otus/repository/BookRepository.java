package ru.otus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends PagingAndSortingRepository<Book,Long> {


    Optional<Book> findById(Long id);
    void deleteById(Long id);
    void delete(Book book);
    List<Book> findByTitle(String title);
    List<Book> findAll();
    Page<Book> findAll(Pageable pageable);

    @Modifying
    @Query("update Book b set b.title = :title where b.id = :id")
    void updateBookTitleById(@Param("id") Long id,@Param("title") String title);
}
