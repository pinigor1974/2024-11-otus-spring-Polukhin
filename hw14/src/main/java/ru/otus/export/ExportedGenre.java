package ru.otus.export;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genres")
@Data
public class ExportedGenre {

    @Id
    private String id;

    private String name;

    public ExportedGenre(String name) {
        this.name = name;
    }

}
