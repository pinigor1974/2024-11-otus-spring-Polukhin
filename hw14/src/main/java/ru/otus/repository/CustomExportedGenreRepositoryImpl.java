package ru.otus.repository;

import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import ru.otus.export.ExportedGenre;

@RequiredArgsConstructor
@Component
public class CustomExportedGenreRepositoryImpl implements CustomExportedGenreRepository {
	
	
	private final MongoTemplate mongoTemplate;

	@Override
	public List<ExportedGenre> findByName(String name) {
		var query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return this.mongoTemplate.find(query, ExportedGenre.class);
	}

}
