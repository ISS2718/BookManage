package com.library_system.book_management.modules.authors.controllers;

import com.library_system.book_management.modules.authors.dtos.CreateAuthorDto;
import com.library_system.book_management.modules.authors.dtos.RecoveryAuthorDto;
import com.library_system.book_management.modules.authors.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<RecoveryAuthorDto>> getAuthors() {
        return ResponseEntity.ok(authorService.getAuthors());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<RecoveryAuthorDto>> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<RecoveryAuthorDto> createAuthor(@RequestBody CreateAuthorDto createAuthorDto) {
        return new ResponseEntity<>(authorService.createAuthor(createAuthorDto), HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<List<RecoveryAuthorDto>> createAuthors(@RequestBody List<CreateAuthorDto> createAuthorDtoList) {
        return new ResponseEntity<>(createAuthorDtoList.stream()
                    .map(create -> authorService.createAuthor(create))
                    .collect(Collectors.toList()),
                    HttpStatus.CREATED
        );
    }
}
