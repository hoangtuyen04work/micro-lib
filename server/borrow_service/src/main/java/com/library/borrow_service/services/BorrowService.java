package com.library.borrow_service.services;

import com.library.borrow_service.dtos.responses.BorrowResponse;
import com.library.borrow_service.entities.Borrow;

import java.util.List;

public interface BorrowService {


    boolean borrowBook(List<Long> bookIds, Long userId);

    boolean returnBooks(List<Long> borrowIds);

    List<BorrowResponse> findByUserId(Long userId, String status);

    List<BorrowResponse> findByBookId(Long bookId);

    BorrowResponse toBorrowResponse(Borrow borrow);

    Borrow toBorrow(Borrow borrow);
}