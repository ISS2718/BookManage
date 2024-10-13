package com.library_system.book_management.modules.authors.entities;

import com.library_system.book_management.modules.books.entities.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;

    private String abbreviationName;

    private Date birthday;

    @OneToMany(mappedBy = "mainAuthor")
    private List<Book> booksAsMainAuthor;

    @ManyToMany(mappedBy = "coAuthor")
    private  List<Book> booksAsCoAuthor;
}
