package com.library_system.book_management.modules.dtos;

import com.library_system.book_management.modules.enums.BookState;
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
