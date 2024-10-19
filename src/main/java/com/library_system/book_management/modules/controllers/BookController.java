package com.library_system.book_management.modules.controllers;

import com.library_system.book_management.modules.dtos.CreateBookDto;
import com.library_system.book_management.modules.dtos.RecoveryBookDto;
import com.library_system.book_management.modules.dtos.UpdateBookDto;
import com.library_system.book_management.modules.services.BookService;
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

    @PutMapping(value = "/{id}")
    public  ResponseEntity<RecoveryBookDto> updateBook(@PathVariable Long id, @RequestBody UpdateBookDto updateBookDto) {
        return ResponseEntity.ok(bookService.updateBook(id,updateBookDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
