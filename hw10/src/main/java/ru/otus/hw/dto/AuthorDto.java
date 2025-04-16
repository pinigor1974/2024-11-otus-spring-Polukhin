package ru.otus.hw.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AuthorDto {

    private long id;

    private String fullName;

    public static AuthorDto fromDomainObject(Author a) {
        return AuthorDto.builder().id(a.getId()).fullName(a.getFullName()).build();
    }
}
