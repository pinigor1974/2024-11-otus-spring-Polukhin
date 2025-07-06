package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "book_id")
    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Book book;

    @Column(name = "title", nullable = false)
    private String title;
}
