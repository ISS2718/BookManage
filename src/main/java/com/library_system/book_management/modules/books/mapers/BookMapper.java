package com.library_system.book_management.modules.books.mapers;

import com.library_system.book_management.modules.authors.entities.Author;
import com.library_system.book_management.modules.books.dtos.RecoveryBookDto;
import com.library_system.book_management.modules.books.entities.Book;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookMapper {
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
}