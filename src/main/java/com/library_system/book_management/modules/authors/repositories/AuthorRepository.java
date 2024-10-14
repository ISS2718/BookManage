package com.library_system.book_management.modules.authors.repositories;

import com.library_system.book_management.modules.authors.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByFullName(String fullName);
}
