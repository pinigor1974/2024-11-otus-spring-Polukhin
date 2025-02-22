package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcAuthorRepository implements AuthorRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Author> findAll() {

        return namedParameterJdbcTemplate.query("select * from authors", (rs, rowNum) -> Author.builder()
                .id(rs.getLong("id"))
                .fullName(rs.getString("full_name"))
                .build());
    }

    @Override
    public Optional<Author> findById(long id) {

        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("select * from authors where id = :id",
                new MapSqlParameterSource("id", id),
                (rs, i) -> Author.builder().id(id).fullName(rs.getString("full_name")).build()));
    }

}
