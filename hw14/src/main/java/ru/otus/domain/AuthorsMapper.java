package ru.otus.domain;

public class AuthorsMapper {
    private Long authorId;
    private Long bookId;

    public AuthorsMapper() {
    }
    public AuthorsMapper(Long authorId, Long bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
