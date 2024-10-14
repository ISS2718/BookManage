package com.library_system.book_management.modules.books.controllers;

import com.library_system.book_management.modules.books.dtos.CreateBookDto;
import com.library_system.book_management.modules.books.dtos.RecoveryBookDto;
import com.library_system.book_management.modules.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity<List<RecoveryBookDto>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<RecoveryBookDto>> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<RecoveryBookDto> createBook(@RequestBody CreateBookDto createBookDto) {
        return new ResponseEntity<>(bookService.createBook(createBookDto), HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<List<RecoveryBookDto>> createBooks(@RequestBody List<CreateBookDto> createBookDtoList) {
        return new ResponseEntity<>(createBookDtoList.stream()
                .map(create -> bookService.createBook(create))
                .collect(Collectors.toList()),
                HttpStatus.CREATED
        );
    }
}
