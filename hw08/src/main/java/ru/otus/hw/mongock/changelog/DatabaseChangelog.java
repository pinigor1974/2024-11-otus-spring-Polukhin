package ru.otus.hw.mongock.changelog;

import com.mongodb.client.MongoDatabase;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

@ChangeUnit(id = "client-initializer", order = "001", author = "mongock", transactional = false)
public class DatabaseChangelog extends MongoBaseData {

    @Execution()
    public void dropDb(MongoDatabase db,
                       BookRepository bookRepository,
                       GenreRepository genreRepository,
                       AuthorRepository authorRepository
    ) {
        db.drop();
        setUp();
        authorRepository.saveAll(this.getDbAuthors());
        genreRepository.saveAll(this.getDbGenres());
        bookRepository.saveAll(this.getDbBooks());
    }


    @RollbackExecution
    public void rollback() {
        System.out.println("Rollback не предусмотрен");
    }
}
