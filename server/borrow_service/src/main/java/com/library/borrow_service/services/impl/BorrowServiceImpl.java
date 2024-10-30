package com.library.borrow_service.services.impl;

import com.library.borrow_service.dtos.responses.BorrowResponse;
import com.library.borrow_service.entities.Borrow;
import com.library.borrow_service.repositories.BorrowRepo;
import com.library.borrow_service.repositories.httpclients.BookClient;
import com.library.borrow_service.services.BorrowService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BorrowServiceImpl implements BorrowService {
    BorrowRepo borrowRepo;
    BookClient bookClient;

    @Override
    public boolean borrowBook(List<Long> bookIds, Long userId) {
        bookClient.borrow(bookIds, userId);
        List<String> name = new ArrayList<>();
        for (Long bookId : bookIds) {
            Borrow borrow = Borrow.builder()
                    .bookId(bookId)
                    .userId(userId)
                    .borrowDate(LocalDate.now())
                    .returnDate(LocalDate.now().plusWeeks(1))
                    .status("BORROWED")
                    .build();
            borrowRepo.save(borrow);
        }
        return true;
    }

    @Override
    public boolean borrowBook(Long bookId, Long userId) {
        bookClient.borrow(bookId, userId);
        List<String> name = new ArrayList<>();
            Borrow borrow = Borrow.builder()
                    .bookId(bookId)
                    .userId(userId)
                    .borrowDate(LocalDate.now())
                    .returnDate(LocalDate.now().plusWeeks(1))
                    .status("BORROWED")
                    .build();
            borrowRepo.save(borrow);

        return true;
    }

    @Override
    public boolean returnBooks(List<Long> bookId, Long userId) {
        bookClient.returnBook(bookId, userId);
        return true;
    }

    @Override
    public void returnBook(Long borrowId){
        Borrow borrow = borrowRepo.findById(borrowId).get();
        borrow.setStatus("RETURNED");
        borrowRepo.save(borrow);
    }

    @Override
    public Boolean check(Long userId, Long bookId){
        return borrowRepo.existsByUserIdAndBookId(userId, bookId);
    }

    @Override
    public List<BorrowResponse> findByUserId(Long userId, String status) {
        return borrowRepo.findByUserIdAndStatus(userId, status).stream().map(this::toBorrowResponse).toList();
    }

    @Override
    public List<BorrowResponse> findByBookId(Long bookId) {
        return borrowRepo.findByBookId(bookId).stream().map(this::toBorrowResponse).toList();
    }

    @Override
    public BorrowResponse toBorrowResponse(Borrow borrow){
        return  BorrowResponse.builder()
                .id(borrow.getId())
                .bookId(borrow.getBookId())
                .borrowDate(borrow.getBorrowDate())
                .returnDate(borrow.getReturnDate())
                .status(borrow.getStatus())
                .userId(borrow.getUserId())
                .build();
    }

    @Override
    public Borrow toBorrow(Borrow borrow){
        return  Borrow.builder()
                .id(borrow.getId())
                .bookId(borrow.getBookId())
                .borrowDate(borrow.getBorrowDate())
                .returnDate(borrow.getReturnDate())
                .status(borrow.getStatus())
                .userId(borrow.getUserId())
                .build();
    }
}
