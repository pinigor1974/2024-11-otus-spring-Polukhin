package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class BookDto {

    private Long id;

    private String title;

    private String authorFullName;

    private Long authorId;

    private Set<Long> genreIds  = Collections.emptySet();

    private List<String> genreNames = Collections.emptyList();

    private List<String> commentNames = Collections.emptyList();

}

