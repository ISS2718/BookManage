package com.library_system.book_management.modules.authors.dtos;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public record UpdateAuthorDto (
    @Nullable String fullName,
    @Nullable String abbreviationName,
    @Nullable LocalDate birthday,
    @Nullable List<String> booksAsMainAuthor,
    @Nullable List<String> booksAsCoAuthor
) {}
