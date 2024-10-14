package com.library_system.book_management.modules.books.services;

import com.library_system.book_management.modules.authors.dtos.RecoveryAuthorDto;
import com.library_system.book_management.modules.authors.mappers.AuthorMapper;
import com.library_system.book_management.modules.authors.services.AuthorService;
import com.library_system.book_management.modules.books.dtos.CreateBookDto;
import com.library_system.book_management.modules.books.dtos.RecoveryBookDto;
import com.library_system.book_management.modules.books.dtos.UpdateBookDto;
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

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorMapper authorMapper;

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

    public Optional<RecoveryBookDto> getBookByTitle(@NonNull String title) {
        Optional<Book> b = bookRepository.findByTitle(title);

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

    public RecoveryBookDto updateBook(@NonNull Long id, @NonNull UpdateBookDto updateBookDto) {
        Book b = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        if(updateBookDto.title() != null) {
            b.setTitle(updateBookDto.title());
        }

        if(updateBookDto.edition() != null) {
            b.setEdition(updateBookDto.edition());
        }

        if(updateBookDto.releaseYear() != null) {
            b.setReleaseYear(updateBookDto.releaseYear());
        }

        // This make BD inconsistent. Because updadte book entity, but not the old main Author Entity.
        if(updateBookDto.mainAuthor() != null) {
            RecoveryAuthorDto newMainAuthor = authorService.getAuthorByFullName(updateBookDto.mainAuthor()).orElseThrow(() -> new RuntimeException("Auhtor not found"));

            b.setMainAuthor(authorMapper.mapRecoveryAuthorDtoToAuthor(newMainAuthor));
        }

        // This make BD inconsistent. Because updadte book entity, but not the olds coAuthors Entities.
        if(updateBookDto.coAuthors() != null) {
            List<RecoveryAuthorDto> coAuhtorsList = updateBookDto.coAuthors().stream()
                    .map(coAuthor -> authorService.getAuthorByFullName(coAuthor)
                            .orElseThrow(() -> new RuntimeException("Auhtor not found")))
                    .toList();
            b.setCoAuthors(coAuhtorsList.stream().map(coAuthor -> authorMapper.mapRecoveryAuthorDtoToAuthor(coAuthor)).collect(Collectors.toList()));
        }

        b.setState(updateBookDto.state());

        return bookMapper.mapBookToRecoveryBookDto(bookRepository.save(b));
    }
}
