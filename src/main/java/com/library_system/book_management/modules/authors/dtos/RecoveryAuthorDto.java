package com.library_system.book_management.modules.authors.dtos;

import jakarta.annotation.Nullable;

import java.util.Date;
import java.util.List;

public record RecoveryAuthorDto (
        Long id,
        String fullName,
        @Nullable String abbreviationName,
        @Nullable Date birthday,
        @Nullable List<String> booksAsMainAuthor,
        @Nullable List<String> booksAsCoAuthor
) {}
