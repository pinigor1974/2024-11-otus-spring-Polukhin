package ru.otus.domain;

public class GenresMapper {
    private Long genreId;
    private Long bookId;

    public GenresMapper() {
    }

    public GenresMapper(Long genreId, Long bookId) {
        this.genreId = genreId;
        this.bookId = bookId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public Long getBookId() {
        return bookId;
    }
    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
