package ru.otus.exportfunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.export.ExportedAuthor;
import ru.otus.export.ExportedBook;
import ru.otus.export.ExportedGenre;
import ru.otus.repository.ExportAuthorRepository;
import ru.otus.repository.ExportGenreRepository;

@Component
@RequiredArgsConstructor
public class ExportConvertor {


	public Function<Author, ExportedAuthor> convertToAuthorExported = a -> {
		var expAuthor = new ExportedAuthor();
		expAuthor.setName(a.getName());
		return expAuthor;
	};

	public Function<Genre, ExportedGenre> convertToGenreExported = g -> {
		var expGenre = new ExportedGenre();
		expGenre.setName(g.getName());
		return expGenre;
	};

	public Function<Book, ExportedBook> convertToBookExported = b -> {
		var exportedBook = new ExportedBook();
		exportedBook.setTitle(b.getTitle());
/*
		exportedBook.setAuthors(
				b.getAuthors().stream().map(a -> new ExportedAuthor(a.getName())).toList()
		);
		exportedBook.setGenres(
				b.getGenres().stream().map(g -> new ExportedGenre(g.getName())).toList()
		);*/
		return exportedBook;
	};

}
