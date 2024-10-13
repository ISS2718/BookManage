package com.library_system.book_management.modules.authors.repositories;

import com.library_system.book_management.modules.authors.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepository extends JpaRepository<Author, Long> {
}
