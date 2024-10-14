package com.library_system.book_management.modules.books.entities;

import com.library_system.book_management.modules.authors.entities.Author;
import com.library_system.book_management.modules.books.enums.BookState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String edition;

    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author mainAuthor;

    @ManyToMany
    @JoinTable(
            name = "book_coAuthors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> coAuthors;

    @Enumerated(EnumType.STRING)
    private BookState state;
}