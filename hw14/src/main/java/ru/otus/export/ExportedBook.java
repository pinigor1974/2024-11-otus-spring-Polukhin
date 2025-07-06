package ru.otus.export;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
@Data
public class ExportedBook {

    @Id
    private String id;

    private String title;

    private List<ExportedAuthor> authors;

    private List<ExportedGenre> genres;

    public ExportedBook(String title) {
        this.title = title;
    }
}
