package com.library_system.book_management.modules.authors.entities;

import com.library_system.book_management.modules.books.entities.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String abbreviationName;

    private LocalDate birthday;

    @OneToMany(mappedBy = "mainAuthor")
    private List<Book> booksAsMainAuthor;

    @ManyToMany(mappedBy = "coAuthors")
    private  List<Book> booksAsCoAuthor;
}
