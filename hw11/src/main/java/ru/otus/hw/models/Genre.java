package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "genres")
@EqualsAndHashCode

public class Genre {

    @Id
    private Long id;

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
