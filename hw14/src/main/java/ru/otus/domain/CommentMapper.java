package ru.otus.domain;

public class CommentMapper {
    private Long bookId;
    private Long title;

    public CommentMapper() {
    }

    public CommentMapper(Long bookId, Long title) {
        this.bookId = bookId;
        this.title = title;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getTitle() {
        return title;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setTitle(Long title) {
        this.title = title;
    }
}
