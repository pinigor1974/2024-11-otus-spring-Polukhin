package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import ru.otus.hw.repositories.BookRepository;

@ChangeLog
public class DatabaseChangelog extends MongoBaseData {

    @ChangeSet(order = "001", id = "insertBook", author = "IP")
    public void insertBook(BookRepository repository) {
        setUp();
        dbBooks.forEach(repository::save);

    }
}