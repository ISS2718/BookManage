package com.library_system.book_management.modules.authors.services;

import com.library_system.book_management.modules.authors.dtos.CreateAuthorsDto;
import com.library_system.book_management.modules.authors.dtos.RecoveryAuthorDto;
import com.library_system.book_management.modules.authors.entities.Author;
import com.library_system.book_management.modules.authors.mappers.AuthorMapper;
import com.library_system.book_management.modules.authors.repositories.AuthorRepository;
import com.library_system.book_management.modules.books.services.BookService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookService bookService;

    @Autowired
    AuthorMapper authorMapper;

    public List<RecoveryAuthorDto> getAuthors() {
        List<Author> a = authorRepository.findAll();

        return a.stream()
                .map(authorMapper::mapAuthorToRecoveryAuthorDto)
                .collect(Collectors.toList());
    }

    public Optional<RecoveryAuthorDto> getAuthorById(@NonNull Long id) {
        Optional<Author> a = authorRepository.findById(id);

        return a.map(authorMapper::mapAuthorToRecoveryAuthorDto);
    }
}
