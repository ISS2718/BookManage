package com.library_system.book_management.modules.authors.mappers;

import com.library_system.book_management.modules.authors.dtos.CreateAuthorsDto;
import com.library_system.book_management.modules.authors.dtos.RecoveryAuthorDto;
import com.library_system.book_management.modules.authors.entities.Author;
import com.library_system.book_management.modules.books.entities.Book;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    public RecoveryAuthorDto mapAuthorToRecoveryAuthorDto(@NonNull Author author) {
        return new RecoveryAuthorDto(
            author.getId(),
            author.getFullName(),
            author.getAbbreviationName(),
            author.getBirthday(),
            Optional.ofNullable(author.getBooksAsMainAuthor())
                    .orElse(Collections.emptyList()).stream()
                    .map(Book::getTitle).collect(Collectors.toList()),
            Optional.ofNullable(author.getBooksAsCoAuthor())
                    .orElse(Collections.emptyList()).stream()
                    .map(Book::getTitle).collect(Collectors.toList())
        );
    }
}
