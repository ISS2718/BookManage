package com.library_system.book_management.modules.books.mappers;

import com.library_system.book_management.modules.authors.entities.Author;
import com.library_system.book_management.modules.authors.mappers.AuthorMapper;
import com.library_system.book_management.modules.authors.services.AuthorService;
import com.library_system.book_management.modules.books.dtos.RecoveryBookDto;
import com.library_system.book_management.modules.books.entities.Book;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    @Autowired
    AuthorService authorService;

    @Autowired
    AuthorMapper authorMapper;

    public RecoveryBookDto mapBookToRecoveryBookDto(@NonNull Book book) {
        return new RecoveryBookDto(
            book.getId(),
            book.getTitle(),
            book.getEdition(),
            book.getReleaseYear(),
            Optional.ofNullable(book.getMainAuthor()).map(Author::getFullName).orElse(null),
            Optional.ofNullable(book.getCoAuthors()).orElse(Collections.emptyList()).stream().map(Author::getFullName).collect(Collectors.toList()),
            book.getState()
        );
    }

    public Book mapRecoveryBookDtoToBook(@NonNull RecoveryBookDto recoveryBookDto) {
        Book b = Book.builder()
                .id(recoveryBookDto.id())
                .title(recoveryBookDto.title())
                .edition(recoveryBookDto.edition())
                .releaseYear(recoveryBookDto.releaseYear())
                .state(recoveryBookDto.state())
                .build();

        if(recoveryBookDto.mainAuthor() != null && !recoveryBookDto.mainAuthor().isEmpty()) {
            Author mainAuthor = authorService.getAuthorByFullName(recoveryBookDto.mainAuthor())
                    .map(authorMapper::mapRecoveryAuthorDtoToAuthor)
                    .orElseThrow(() -> new RuntimeException("Main author not found: " + recoveryBookDto.mainAuthor()));

            b.setMainAuthor(mainAuthor);
        }

        if(recoveryBookDto.coAuthors() != null && !recoveryBookDto.coAuthors().isEmpty()) {
            List<Author> coAuthors = recoveryBookDto.coAuthors().stream()
                    .map(coAuthor -> authorService.getAuthorByFullName(coAuthor)
                            .map(authorMapper::mapRecoveryAuthorDtoToAuthor)
                    .orElseThrow(() -> new RuntimeException("CoAuthor not found: " + coAuthor)))
                    .toList();

            b.setCoAuthors(coAuthors);
        }

        return b;
    }
}