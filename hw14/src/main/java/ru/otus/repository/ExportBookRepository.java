package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.export.ExportedBook;

public interface ExportBookRepository extends MongoRepository<ExportedBook, String>  {

	
}
