package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.export.ExportedGenre;

public interface ExportGenreRepository extends MongoRepository<ExportedGenre,String>, CustomExportedGenreRepository {
	
}
