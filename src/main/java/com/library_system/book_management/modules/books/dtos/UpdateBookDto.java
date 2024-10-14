package com.library_system.book_management.modules.books.dtos;

import com.library_system.book_management.modules.books.enums.BookState;
import org.springframework.lang.Nullable;

import java.util.List;

public record UpdateBookDto (
    @Nullable String title,
    @Nullable String edition,
    @Nullable Integer releaseYear,
    @Nullable String mainAuthor,
    @Nullable List<String> coAuthors,
    BookState state
) {}
