package com.library_system.book_management.modules.books.services;

import com.library_system.book_management.modules.authors.repositories.AuthorRepository;
import com.library_system.book_management.modules.books.dtos.RecoveryBookDto;
import com.library_system.book_management.modules.books.entities.Book;
import com.library_system.book_management.modules.books.mapers.BookMapper;
import com.library_system.book_management.modules.books.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMaper;

    public List<RecoveryBookDto> getBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(bookMaper::mapBookToRecoveryBookDto)
                .collect(Collectors.toList());
    }

    public Optional<RecoveryBookDto> getBookById(Long id) {
        Optional<Book> b = bookRepository.findById(id);

        return b.map(bookMaper::mapBookToRecoveryBookDto);
    }
}
