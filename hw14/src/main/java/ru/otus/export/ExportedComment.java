package ru.otus.export;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
@Data
public class ExportedComment {

    @Id
    private String id;

    @DBRef
    private ExportedBook book;

    private String title;
}
