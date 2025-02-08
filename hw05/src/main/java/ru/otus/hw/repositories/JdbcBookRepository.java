package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final GenreRepository genreRepository;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<Book> findById(long id) {

        return Optional.ofNullable(namedParameterJdbcTemplate.query("select * from book where id=:id"
                , Map.of("id", id)
                , new BookResultSetExtractor()));
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcTemplate.update("delete from book where id=:id"
                , Map.of("id", id)
        );
    }

    private List<Book> getAllBooksWithoutGenres() {
        return new ArrayList<>();
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return new ArrayList<>();
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        // Добавить книгам (booksWithoutGenres) жанры (genres) в соответствии со связями (relations)
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        //...

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        //...

        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        // Использовать метод batchUpdate
        namedParameterJdbcTemplate.batchUpdate(
                "insert into books_genres  "
        , new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {

                    }

                    public int getBatchSize() {
                        return book.getGenres().size();
                    }
                }
                    );
    }

    private void removeGenresRelationsFor(Book book) {
        namedParameterJdbcTemplate.update(
                "delete from books_genres where book_id = :book_id"
        , new MapSqlParameterSource("book_id", book.getId()));
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return null;
        }
    }

    // Использовать для findById
    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            return Book.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .build();
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }
}
