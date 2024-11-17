package com.library.borrow_service.services;

import com.library.borrow_service.dtos.responses.BorrowResponse;
import com.library.borrow_service.dtos.responses.PageResponse;
import com.library.borrow_service.entities.Borrow;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BorrowService {

    PageResponse<BorrowResponse> getRecentlyAction(Integer page, Integer size);

    PageResponse<Long> getRecently(Integer userId, Integer page, Integer size);

    PageResponse<Long> topBorrow(Integer page, Integer size);

    //return a book of a user
    boolean returnBook(Long bookId, Long userId);

    boolean borrowBook(List<Long> bookIds, Long userId);

    boolean borrowBook(Long bookId, Long userId);

    boolean returnBooks(List<Long> borrowIds, Long id);

    void returnBook(Long borrowId);

    Boolean check(Long userId, Long bookId);

    List<BorrowResponse> findByUserId(Long userId, String status);

    List<BorrowResponse> findByBookId(Long bookId);
}