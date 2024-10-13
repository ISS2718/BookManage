package com.library_system.book_management.modules.books.repositories;

import com.library_system.book_management.modules.books.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
