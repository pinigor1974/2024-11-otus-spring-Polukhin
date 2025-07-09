package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Author;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDto {

    private long id;

    private String data;

    public static AuthorDto fromDomainObject(Author a) {
        return AuthorDto.builder().id(a.getId()).fullName(a.getFullName()).build();
    }

}
