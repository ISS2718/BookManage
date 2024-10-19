package com.library_system.book_management.modules.authors.mappers;

import com.library_system.book_management.modules.authors.dtos.RecoveryAuthorDto;
import com.library_system.book_management.modules.authors.entities.Author;
import com.library_system.book_management.modules.entities.Book;
import com.library_system.book_management.modules.mappers.BookMapper;
import com.library_system.book_management.modules.services.BookService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    @Autowired
    BookService bookService;

    @Autowired
    BookMapper bookMapper;

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

    public Author mapRecoveryAuthorDtoToAuthor(@NonNull RecoveryAuthorDto recoveryAuthorDto) {
        Author a = Author.builder()
                .id(recoveryAuthorDto.id())
                .fullName(recoveryAuthorDto.fullName())
                .abbreviationName(recoveryAuthorDto.abbreviationName())
                .birthday(recoveryAuthorDto.birthday())
                .build();

        if(recoveryAuthorDto.booksAsMainAuthor() != null && !recoveryAuthorDto.booksAsMainAuthor().isEmpty()) {
            List<Book> bookListAsMainAuthored = recoveryAuthorDto.booksAsMainAuthor().stream()
                    .map(bookName -> bookService.getBookByTitle(bookName)
                            .map(bookMapper::mapRecoveryBookDtoToBook)
                            .orElseThrow(() -> new RuntimeException("Book not Found: " + bookName)))
                    .toList();

            a.setBooksAsMainAuthor(bookListAsMainAuthored);
        }

        if(recoveryAuthorDto.booksAsCoAuthor() != null && !recoveryAuthorDto.booksAsCoAuthor().isEmpty()) {
            List<Book> bookListAsCoAuthored = recoveryAuthorDto.booksAsCoAuthor().stream()
                    .map(bookName -> bookService.getBookByTitle(bookName)
                            .map(bookMapper::mapRecoveryBookDtoToBook)
                            .orElseThrow(() -> new RuntimeException("Book not Found: " + bookName)))
                    .toList();

           a.setBooksAsCoAuthor(bookListAsCoAuthored);
        }

        return a;
    }
}
