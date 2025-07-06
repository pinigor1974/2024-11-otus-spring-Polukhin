package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.export.ExportedAuthor;

public interface ExportAuthorRepository extends MongoRepository<ExportedAuthor,String>, CustomExportedAuthorRepository {

}
