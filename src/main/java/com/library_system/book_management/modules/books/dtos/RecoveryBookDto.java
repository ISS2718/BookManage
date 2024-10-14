package com.library_system.book_management.modules.books.dtos;

import com.library_system.book_management.modules.books.enums.BookState;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.List;


public record RecoveryBookDto (
        Long id,
        String title,
        String edition,
        Integer releaseYear,
        @Nullable String mainAuthor,
        @Nullable List<String> coAuthors,
        BookState state
) {}
