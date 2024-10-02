package com.library.book_service.repositories;

import com.library.book_service.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.name LIKE :name")
    Page<Book> findByName(@Param("name") String name, Pageable pageable);
    Page<Book> findByNameContaining(String name, Pageable pageable);

}