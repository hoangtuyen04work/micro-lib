package com.library.borrow_service.repositories;

import com.library.borrow_service.entities.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepo extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUserIdAndStatus(Long userId, String status);
    List<Borrow> findByBookId(Long bookId);
    List<Borrow> findByUserId(Long userId);
    Boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
