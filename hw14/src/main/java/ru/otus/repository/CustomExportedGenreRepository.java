package ru.otus.repository;

import java.util.List;
import ru.otus.export.ExportedGenre;

public interface CustomExportedGenreRepository {

	List<ExportedGenre> findByName(String name);
}
