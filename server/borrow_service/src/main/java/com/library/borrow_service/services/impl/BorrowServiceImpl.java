package com.library.borrow_service.services.impl;

import com.library.borrow_service.dtos.responses.BorrowResponse;
import com.library.borrow_service.entities.Borrow;
import com.library.borrow_service.repositories.BorrowRepo;
import com.library.borrow_service.repositories.httpclients.BookClient;
import com.library.borrow_service.services.BorrowService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
        bookClient.borrow(bookIds);
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
    public boolean returnBooks(List<Long> borrowIds) {
        List<Long> bookIds = new ArrayList<>();
        for(Long id : borrowIds){
            bookIds.add(borrowRepo.findById(id).get().getBookId());
        }
        bookClient.returnBook(bookIds);
        return true;
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
