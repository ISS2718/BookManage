package com.library_system.book_management.modules.authors.dtos;

import com.library_system.book_management.modules.dtos.CreateBookDto;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public record CreateAuthorDto(
        String fullName,
        @Nullable String abbreviationName,
        @Nullable LocalDate birthday,
        @Nullable List<CreateBookDto> booksAsMainAuthor,
        @Nullable List<CreateBookDto> booksAsCoAuthor
) {}
