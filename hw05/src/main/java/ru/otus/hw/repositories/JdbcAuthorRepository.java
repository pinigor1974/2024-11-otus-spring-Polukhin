package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcAuthorRepository implements AuthorRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Author> findAll() {

        return jdbcTemplate.query("select id, fullname from authors", (rs, rowNum) -> Author.builder()
                .id(rs.getLong("id"))
                .fullName(rs.getString("full"))
                .build());
    }

    @Override
    public Optional<Author> findById(long id) {

        return Optional.ofNullable(jdbcTemplate.queryForObject("select id, fullname from authors where id = :id",
                Map.of("id", id),
                Author.class));
    }

}
