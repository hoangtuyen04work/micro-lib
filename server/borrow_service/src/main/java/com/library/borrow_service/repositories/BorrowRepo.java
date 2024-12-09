package com.library.borrow_service.repositories;

import com.library.borrow_service.entities.Borrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepo extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUserIdAndStatus(Long userId, String status);
    List<Borrow> findByBookId(Long bookId);
    List<Borrow> findByUserId(Long userId);
    Boolean existsByUserIdAndBookIdAndStatus(Long userId, Long bookId, String status);
    @Query("SELECT b.bookId FROM Borrow b ORDER BY b.borrowDate DESC")
    Page<Long> getAllBookId(Pageable pageable);
    Page<Borrow> findAll(Pageable pageable);
    Page<Long> findByUserId(Long userId, Pageable pageable);
    List<Borrow> findByUserIdAndBookId(Long userId, Long bookId);
    Boolean existsByUserIdAndBookId(Long userId, Long bookId);
    List<Borrow> findByUserIdAndBookIdAndStatus(Long userId, Long bookId, String status);
}
