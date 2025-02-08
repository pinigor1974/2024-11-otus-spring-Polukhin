package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class JdbcGenreRepository implements GenreRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Genre> findAll() {
        return namedParameterJdbcTemplate.queryForList(
                "select * from genre"
                , new MapSqlParameterSource()
                , Genre.class);
    }

    @Override
    public List<Genre> findAllByIds(Set<Long> ids) {

        return namedParameterJdbcTemplate.queryForList(
                "select * from genre where id in (:ids) "
                , new MapSqlParameterSource("ids", ids)
                , Genre.class);
    }

    private static class GnreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            return Genre.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }
}
