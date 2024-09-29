package com.library.book_service.repositories;

import com.library.book_service.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findAll(List<Long> id);
    @Query("SELECT B FROM Book B WHERE b.name LIKE %:name%")
    Page<Book> searchBookBy(@Param("name") String name, Pageable pageable);
}
