package com.batuhan.library.repository;

import com.batuhan.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // List<Book> findByTitle(String title);
    // List<Book> findByTitleContaining(String title);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByTitleAndAuthor(String title, String author);
}