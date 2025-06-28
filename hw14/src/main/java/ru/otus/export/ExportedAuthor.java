package ru.otus.export;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
@Data
public class ExportedAuthor {

    @Id
    private String id;

    private String name;

    public ExportedAuthor(String name) {
        this.name = name;
    }

}
