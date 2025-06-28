package ru.otus.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.export.ExportedAuthor;
import ru.otus.export.ExportedBook;
import ru.otus.export.ExportedGenre;
import ru.otus.exportfunctions.ExportConvertor;
import ru.otus.listeners.AuthorProcessListener;
import ru.otus.listeners.AuthorReadListener;
import ru.otus.listeners.ExportedAuthorWriteListener;
import ru.otus.listeners.BatchExecutionJobListener;
import ru.otus.listeners.BookProcessListener;
import ru.otus.listeners.BookReadListener;
import ru.otus.listeners.ExportedBookWriteListener;
import ru.otus.listeners.ExportedGenreWriteListener;
import ru.otus.listeners.GenreProcessListener;
import ru.otus.listeners.GenreReadListener;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.ExportBookRepository;
import ru.otus.repository.ExportGenreRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.ExportAuthorRepository;
import ru.otus.repository.GenreRepository;
import java.util.ArrayList;
import java.util.Map;



@Configuration
public class JobConfig {
	
	
	private static final int CHUNK_BOOK_EXPORT_SIZE = 5;
	private static final int CHUNK_AUTHOR_EXPORT_SIZE = 1;
	private static final int CHUNK_GENRE_EXPORT_SIZE = 2;
	
	public static final String EXPORT_BOOKS_JOB = "ExportBooksJob";
	public static final String EXPORT_AUTHORS_JOB = "ExportAuthorsJob";
	public static final String EXPORT_GENRES_JOB = "ExportGenresJob";
	

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private ExportAuthorRepository exportAuthorRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private ExportGenreRepository exportGenreRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ExportBookRepository exportBookRepository;

	@Autowired
	private BookReadListener bookReadListener;
	
	@Autowired
	private AuthorReadListener authorReadListener;
	
	@Autowired
	private GenreReadListener genreReadListener;

	@Autowired
	private ExportedBookWriteListener exportedBookWriteListener;
	
	@Autowired
	private ExportedAuthorWriteListener exportedAuthorWriteListener;
	
	@Autowired
	private ExportedGenreWriteListener exportedGenreWriteListener;

	@Autowired
	private BookProcessListener bookProcessListener;
	
	@Autowired
	private AuthorProcessListener authorProcessListener;
	
	@Autowired
	private GenreProcessListener genreProcessListener;
	
	@Autowired
	private BatchExecutionJobListener batchExecutionJobListener;
	
	@Autowired
	private ExportConvertor exportConverter;

	@StepScope
	@Bean
	public RepositoryItemReader<Book> bookReader() {
		Map<String, Sort.Direction> sorts = Map.of("id", Direction.ASC);

		var reader = new RepositoryItemReaderBuilder<Book>().name("bookReader").repository(bookRepository).sorts(sorts)
				.pageSize(CHUNK_BOOK_EXPORT_SIZE).methodName("findAll").arguments(new ArrayList<>()).build();

		return reader;
	}

	@StepScope
	@Bean
	public RepositoryItemReader<Author> authorReader() {
		Map<String, Sort.Direction> sorts = Map.of("id", Direction.ASC);

		var reader = new RepositoryItemReaderBuilder<Author>().name("authorReader").repository(authorRepository)
				.sorts(sorts).methodName("findAll").arguments(new ArrayList<>()).build();

		return reader;
	}

	@StepScope
	@Bean
	public RepositoryItemReader<Genre> genreReader() {
		Map<String, Sort.Direction> sorts = Map.of("id", Direction.ASC);

		var reader = new RepositoryItemReaderBuilder<Genre>().name("genreReader").repository(genreRepository)
				.sorts(sorts).methodName("findAll").arguments(new ArrayList<>()).build();

		return reader;
	}

	@StepScope
	@Bean
	public RepositoryItemWriter<ExportedBook> bookWriter() {

		return new RepositoryItemWriterBuilder<ExportedBook>().repository(exportBookRepository).methodName("save")
				.build();

	}

	@StepScope
	@Bean
	public RepositoryItemWriter<ExportedAuthor> authorWriter() {

		return new RepositoryItemWriterBuilder<ExportedAuthor>().repository(exportAuthorRepository).methodName("save")
				.build();

	}

	@StepScope
	@Bean
	public RepositoryItemWriter<ExportedGenre> genreWriter() {

		return new RepositoryItemWriterBuilder<ExportedGenre>().repository(exportGenreRepository).methodName("save")
				.build();

	}

	@StepScope
	@Bean
	public ItemProcessor<Book, ExportedBook> bookExportProcessor() {
		return exportConverter.convertToBookExported::apply;
	}

	@StepScope
	@Bean
	public ItemProcessor<Author, ExportedAuthor> authorExportProcessor() {
		return exportConverter.convertToAuthorExported::apply;
	}

	@StepScope
	@Bean
	public ItemProcessor<Genre, ExportedGenre> genreExportProcessor() {
		return exportConverter.convertToGenreExported::apply;
	}

	@Bean
	public Job exportAuthorJob(Step transformAuthorStep) {
		return jobBuilderFactory.get(EXPORT_AUTHORS_JOB).incrementer(new RunIdIncrementer()).flow(transformAuthorStep)
				.end().listener(batchExecutionJobListener).build();
	}

	@Bean
	public Job exportBookJob(Step transformBookStep) {
		return jobBuilderFactory.get(EXPORT_BOOKS_JOB).incrementer(new RunIdIncrementer()).flow(transformBookStep)
				.end().listener(batchExecutionJobListener).build();
	}
	
	@Bean
	public Job exportGenreJob(Step transformGenreStep) {
		return jobBuilderFactory.get(EXPORT_GENRES_JOB).incrementer(new RunIdIncrementer()).flow(transformGenreStep)
				.end().listener(batchExecutionJobListener).build();
	}

	@Bean
	public Step transformAuthorStep(ItemReader<Author> reader, RepositoryItemWriter<ExportedAuthor> writer,
			ItemProcessor<Author, ExportedAuthor> itemProcessor) {
		return stepBuilderFactory.get("step1").<Author, ExportedAuthor>chunk(CHUNK_AUTHOR_EXPORT_SIZE).reader(reader).processor(itemProcessor)
				.writer(writer).listener(authorReadListener).listener(exportedAuthorWriteListener).listener(authorProcessListener).build();

	}

	@Bean
	public Step transformBookStep(ItemReader<Book> reader, RepositoryItemWriter<ExportedBook> writer,
			ItemProcessor<Book, ExportedBook> itemProcessor) {
		return stepBuilderFactory.get("step1").<Book, ExportedBook>chunk(CHUNK_BOOK_EXPORT_SIZE).reader(reader)
				.processor(itemProcessor).writer(writer).listener(bookReadListener).listener(exportedBookWriteListener)
				.listener(bookProcessListener).build();
	}
	
	@Bean
	public Step transformGenreStep(ItemReader<Genre> reader, RepositoryItemWriter<ExportedGenre> writer, ItemProcessor<Genre,ExportedGenre> itemProcessor) {
		return stepBuilderFactory.get("step1").<Genre, ExportedGenre>chunk(CHUNK_GENRE_EXPORT_SIZE).reader(reader)
				.processor(itemProcessor).writer(writer).listener(genreReadListener).listener(exportedGenreWriteListener)
				.listener(genreProcessListener).build();
	}


	
}
