package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import ru.otus.hw.repositories.BookRepository;

@ChangeUnit(id="client-initializer", order = "001", author = "mongock")
public class DatabaseChangelog {

    @Execution
    public void dropDb() {
        int aaa = 1;
    }

/*
    @ChangeSet(order = "002", id = "insertBook", author = "IP")
    public void insertBook(BookRepository repository) {
        setUp();
        dbBooks.forEach(repository::save);

    }
    */

}