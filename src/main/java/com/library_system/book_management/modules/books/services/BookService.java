package com.library_system.book_management.modules.books.services;

import com.library_system.book_management.modules.authors.services.AuthorService;
import com.library_system.book_management.modules.books.dtos.CreateBookDto;
import com.library_system.book_management.modules.books.dtos.RecoveryBookDto;
import com.library_system.book_management.modules.books.entities.Book;
import com.library_system.book_management.modules.books.mappers.BookMapper;
import com.library_system.book_management.modules.books.repositories.BookRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    // Generate a Circular Dependency
//    @Autowired
//    private AuthorService authorService;

    @Autowired
    private BookMapper bookMapper;

    public List<RecoveryBookDto> getBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(bookMapper::mapBookToRecoveryBookDto)
                .collect(Collectors.toList());
    }

    public Optional<RecoveryBookDto> getBookById(Long id) {
        Optional<Book> b = bookRepository.findById(id);

        return b.map(bookMapper::mapBookToRecoveryBookDto);
    }
    
    public RecoveryBookDto createBook(@NonNull CreateBookDto createBookDto) {
        Book newBook = Book.builder()
                .title(createBookDto.title())
                .edition(createBookDto.edition())
                .releaseYear(createBookDto.releaseYear())
                .state(createBookDto.state())
                .build();

        Book savedBook = bookRepository.save(newBook);

//        if (createBookDto.mainAuthor() != null) {
//
//        }
//
//        if (createBookDto.coAuthors() != null && !createBookDto.coAuthors().isEmpty()) {
//
//        }

        return bookMapper.mapBookToRecoveryBookDto(savedBook);
    }
}
