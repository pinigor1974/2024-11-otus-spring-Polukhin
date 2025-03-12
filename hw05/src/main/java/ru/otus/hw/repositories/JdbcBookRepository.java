package ru.otus.hw.repositories;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {
    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<Book> findById(long id) {
        var bookRecords = namedParameterJdbcTemplate.query("""
                        select b.id as b_id, b.title as b_title, a.id as a_id,
                         a.full_name as a_full_name, g.id g_id, g.name as g_name from books b
                        left outer join  authors a on a.id = b.author_id
                        left outer join  books_genres bg on bg.book_id = b.id
                        left outer join  genres g on bg.genre_id = g.id  where b.id = :id
                        """,
                new MapSqlParameterSource(Map.of("id", id)),
                (rs, i) -> BookRecord.builder()
                        .bId(rs.getLong("b_id"))
                        .bTitle(rs.getString("b_title"))
                        .aId(rs.getLong("a_id"))
                        .aFullName(rs.getString("a_full_name"))
                        .gId(rs.getLong("g_id"))
                        .gName(rs.getString("g_name"))
                        .build());
        var bookRecord = bookRecords.isEmpty() ? null : bookRecords.iterator().next();
        return bookRecords.isEmpty() ? Optional.empty() : Optional.of(Book.builder()
                .id(bookRecord.bId())
                .title(bookRecord.bTitle())
                .author(Author.builder().id(bookRecord.aId()).fullName(bookRecord.aFullName()).build())
                .genres(bookRecords.stream().map(br -> Genre.builder().id(br.gId()).name(br.gName()).build()).toList())
                .build());
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
        namedParameterJdbcTemplate.update("delete from books where id=:id"
                , Map.of("id", id)
        );
    }

    private List<Book> getAllBooksWithoutGenres() {
        return namedParameterJdbcTemplate.query(
                "select * from books",
                new MapSqlParameterSource(),
                (rs, i) -> Book.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .author(
                                authorRepository.findById(rs.getLong("author_id")).get()
                        ).build());

    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return namedParameterJdbcTemplate.query(
                "select *  from books_genres"
                , new MapSqlParameterSource()
                , (rs, numRow) -> new BookGenreRelation(rs.getLong("book_id"), rs.getLong("genre_id")));
    }


    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        // Добавить книгам (booksWithoutGenres) жанры (genres) в соответствии со связями (relations)
        var mapRelations = relations.stream().collect(Collectors.groupingBy(r -> r.bookId));
        var mapGenres = genres.stream().collect(Collectors.toMap(Genre::getId, identity()));
        booksWithoutGenres.forEach(b -> b.setGenres(
                mapRelations.get(b.getId()).stream().map(r -> mapGenres.get(r.genreId)).toList()
        ));
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                "insert into books (title, author_id) values(:title, :author_id )",
                new MapSqlParameterSource(Map.of("title", book.getTitle(), "author_id", book.getAuthor().getId())),
                keyHolder
        );
        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        //...
        var updted = namedParameterJdbcTemplate.update(
                "update books set title = :title, author_id=:author_id where id = :id",
                new MapSqlParameterSource(
                        Map.of("title", book.getTitle(), "author_id", book.getAuthor().getId(), "id", book.getId())
                ));
        if (updted == 0) {
            throw new EntityNotFoundException("book not found : " + book.getId());
        }
        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        // Использовать метод batchUpdate
        namedParameterJdbcTemplate.getJdbcTemplate().batchUpdate(
                "insert into books_genres (book_id, genre_id) values(? , ?)  "
                , new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, book.getId());
                        ps.setLong(2, book.getGenres().get(i).getId());
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

    private List<BookGenreRelation> getBookGenreRelationFor(Book book) {
        return namedParameterJdbcTemplate.query(
                "select *  from books_genres where book_id = :book_id"
                , new MapSqlParameterSource("book_id", book.getId())
                , (rs, numRow) -> new BookGenreRelation(rs.getLong("book_id"), rs.getLong("genre_id")));
    }

    /*
    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return null;
        }
    }
*/
    // Использовать для findById
    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            return rs.next() ? Book.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .author(Author.builder().id(rs.getLong("author_id")).build())
                    .build() : null;
        }
    }

    private record BookGenreRelation(long bookId, long genreId) {
    }

    @Builder
    private record BookRecord(Long bId, String bTitle, Long aId, String aFullName, Long gId, String gName) {
    }
}
