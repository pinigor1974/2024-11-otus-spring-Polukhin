package ru.otus.domain;

public class BookMapper {
    private String title;
    private Long authorId;
    private Long genreId;

    public BookMapper() {
    }

    public BookMapper(String title, Long authorId, Long genreId) {
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public String getTitle() {
        return title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }
}
