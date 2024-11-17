package com.library.borrow_service.services.impl;

import com.library.borrow_service.dtos.responses.BorrowResponse;
import com.library.borrow_service.dtos.responses.PageResponse;
import com.library.borrow_service.entities.Borrow;
import com.library.borrow_service.mapping.Mapping;
import com.library.borrow_service.repositories.BorrowRepo;
import com.library.borrow_service.repositories.httpclients.BookClient;
import com.library.borrow_service.services.BorrowService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BorrowServiceImpl implements BorrowService {
    BorrowRepo borrowRepo;
    BookClient bookClient;
    Mapping mapping;

    @Override
    public PageResponse<BorrowResponse> getRecentlyAction(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("borrowDate").descending());
        Page<Borrow> pages = borrowRepo.findAll(pageable);
        return mapping.toPageResponseFromBorrow(pages);
    }

    //get bookids of a recently borrowed of user by UserId
    @Override
    public PageResponse<Long> getRecently(Integer userId, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("borrowDate").descending());
        Page<Long> pages = borrowRepo.findByUserId(Long.valueOf(userId), pageable);
        return mapping.toPageResponse(pages);
    }

    // fetch top action borrow or return moet recently
    @Override
    public PageResponse<Long> topBorrow(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("borrowDate").descending());
        Page<Long> pages = borrowRepo.getAllBookId(pageable);
        return mapping.toPageResponse(pages);
    }

    //return a book of a user
    @Override
    public boolean returnBook(Long bookId, Long userId) {
        bookClient.returnBook(bookId, userId);
        Borrow borrow = borrowRepo.findByUserIdAndBookId(userId, bookId);
        borrow.setStatus("RETURNED");
        borrowRepo.save(borrow);
        return true;
    }

    //return list of Book of a user
    @Override
    public boolean borrowBook(List<Long> bookIds, Long userId) {
        bookClient.borrow(bookIds, userId);
        for (Long bookId : bookIds) {
            Borrow borrow = mapping.toBorrow(bookId, userId);
            borrowRepo.save(borrow);
        }
        return true;
    }

    @Override
    public boolean borrowBook(Long bookId, Long userId) {
        bookClient.borrow(bookId, userId);
        borrowRepo.save(mapping.toBorrow(bookId, userId));
        return true;
    }

    @Override
    public boolean returnBooks(List<Long> bookId, Long userId) {
        bookClient.returnBook(bookId, userId);
        return true;
    }

    // return book without user Id
    @Override
    public void returnBook(Long borrowId){
        Borrow borrow = borrowRepo.findById(borrowId).get();
        borrow.setStatus("RETURNED");
        borrowRepo.save(borrow);
    }

    @Override
    public Boolean check(Long userId, Long bookId){
        return borrowRepo.existsByUserIdAndBookIdAndStatus(userId, bookId, "BORROWED");
    }

    @Override
    public List<BorrowResponse> findByUserId(Long userId, String status) {
        return borrowRepo.findByUserIdAndStatus(userId, status).stream().map(mapping::toBorrowResponse).toList();
    }

    @Override
    public List<BorrowResponse> findByBookId(Long bookId) {
        return borrowRepo.findByBookId(bookId).stream().map(mapping::toBorrowResponse).toList();
    }
}
