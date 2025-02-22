package ru.otus.hw.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DBConfiguration {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@NotNull DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
