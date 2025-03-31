package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;
import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    @Query("from Comment c where c.book.id = :bookId")
    List<Comment> findAll(@Param("bookId") long bookId);
}
