package com.library_system.book_management.modules.books.dtos;

import com.library_system.book_management.modules.authors.dtos.CreateAuthorDto;
import com.library_system.book_management.modules.books.enums.BookState;
import jakarta.annotation.Nullable;

import java.util.List;

public record CreateBookDto(
        String title,
        @Nullable String edition,
        @Nullable Integer releaseYear,
        @Nullable CreateAuthorDto mainAuthor,
        @Nullable List<CreateAuthorDto> coAuthors,
        BookState state
) {}
