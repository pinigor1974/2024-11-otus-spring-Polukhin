package ru.otus.repository;

import java.util.List;
import ru.otus.export.ExportedAuthor;

public interface CustomExportedAuthorRepository {

	List<ExportedAuthor> findByName(String name);

}
