package ru.otus.hw.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor()
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
@Table(name = "comments")


public class Comment {

    @Id
    private Long id;

    @lombok.NonNull
    private String data;

    private Long bookId;

    @Override
    public String toString() {
        return data;
    }
}
