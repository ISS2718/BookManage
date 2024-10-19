package com.library_system.book_management.modules.authors.services;

import com.library_system.book_management.modules.authors.dtos.CreateAuthorDto;
import com.library_system.book_management.modules.authors.dtos.RecoveryAuthorDto;
import com.library_system.book_management.modules.authors.dtos.UpdateAuthorDto;
import com.library_system.book_management.modules.authors.entities.Author;
import com.library_system.book_management.modules.authors.mappers.AuthorMapper;
import com.library_system.book_management.modules.authors.repositories.AuthorRepository;
import com.library_system.book_management.modules.dtos.RecoveryBookDto;
import com.library_system.book_management.modules.mappers.BookMapper;
import com.library_system.book_management.modules.services.BookService;
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

    @Autowired
    BookMapper bookMapper;

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

    public Optional<RecoveryAuthorDto> getAuthorByFullName(@NonNull String fullName) {
        Optional<Author> a = authorRepository.findByFullName(fullName);

        return a.map(authorMapper::mapAuthorToRecoveryAuthorDto);
    }

    public RecoveryAuthorDto createAuthor(@NonNull CreateAuthorDto createAuthorDto) {
        Author newAuthor = Author.builder()
                .fullName(createAuthorDto.fullName())
                .abbreviationName(createAuthorDto.abbreviationName())
                .birthday(createAuthorDto.birthday())
                .build();

        Author savedAuthor = authorRepository.save(newAuthor);

//        if (createAuthorDto.booksAsMainAuthor() != null && !createAuthorDto.booksAsMainAuthor().isEmpty()) {
//
//        }

//        if (createAuthorDto.booksAsCoAuthor() != null && !createAuthorDto.booksAsCoAuthor().isEmpty()) {
//
//        }

        return authorMapper.mapAuthorToRecoveryAuthorDto(savedAuthor);
    }

    public RecoveryAuthorDto updateAuthor(@NonNull Long id, @NonNull UpdateAuthorDto updateAuthorDto) {
        Author a = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found: " + id));

        if(updateAuthorDto.fullName() != null) a.setFullName(updateAuthorDto.fullName());

        if(updateAuthorDto.abbreviationName() != null) a.setAbbreviationName(updateAuthorDto.abbreviationName());

        if(updateAuthorDto.birthday() != null) a.setBirthday(updateAuthorDto.birthday());

        // TODO: make it so that when updating the authors' main books, it also updates the main authors of the books.
        // This make BD inconsistent. Because update author entity, but not the Books Entities.
        if(updateAuthorDto.booksAsMainAuthor() != null && !updateAuthorDto.booksAsMainAuthor().isEmpty()){
            List<RecoveryBookDto> bookAsMainAuthoredList = updateAuthorDto.booksAsMainAuthor().stream()
                    .map(title -> bookService.getBookByTitle(title)
                            .orElseThrow(() -> new RuntimeException("Book not found: " + title)))
                    .toList();
            a.setBooksAsMainAuthor(bookAsMainAuthoredList.stream().map(book -> bookMapper.mapRecoveryBookDtoToBook(book)).collect(Collectors.toList()));
        }

        // TODO: make it so that when updating the authors' main books, it also updates the main authors of the books.
        // This make BD inconsistent. Because update author entity, but not the Books Entities.
        if(updateAuthorDto.booksAsCoAuthor() != null && !updateAuthorDto.booksAsCoAuthor().isEmpty()){
            List<RecoveryBookDto> bookAsCoAuthoredList = updateAuthorDto.booksAsCoAuthor().stream()
                    .map(title -> bookService.getBookByTitle(title)
                            .orElseThrow(() -> new RuntimeException("Book not found: " + title)))
                    .toList();
            a.setBooksAsCoAuthor(bookAsCoAuthoredList.stream().map(book -> bookMapper.mapRecoveryBookDtoToBook(book)).collect(Collectors.toList()));
        }

        return authorMapper.mapAuthorToRecoveryAuthorDto(authorRepository.save(a));
    }

    public void deleteAuthorById(Long id) {
        if(!authorRepository.existsById(id)) throw new RuntimeException("Author not found");

        authorRepository.deleteById(id);
    }
}
