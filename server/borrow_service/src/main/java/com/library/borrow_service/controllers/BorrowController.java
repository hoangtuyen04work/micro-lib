package com.library.borrow_service.controllers;

import com.library.borrow_service.dtos.ApiResponse;
import com.library.borrow_service.dtos.responses.BorrowResponse;
import com.library.borrow_service.dtos.responses.PageResponse;
import com.library.borrow_service.services.BorrowService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BorrowController {
    BorrowService borrowService;

    @GetMapping("/top")
    public ApiResponse<PageResponse<Long>> getTop(@RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size){
        return ApiResponse.<PageResponse<Long>>builder()
                .data(borrowService.topBorrow(page, size))
                .build();
    }

    @PutMapping("/borrow/{userId}")
    public ApiResponse<Boolean> borrowBook(@RequestBody List<Long> ids, @PathVariable Long userId) {
        return ApiResponse.<Boolean>builder()
                .data(borrowService.borrowBook(ids, userId))
                .build();
    }

    @PutMapping("/borrow/{userId}/{bookId}")
    public ApiResponse<Boolean> borrowBook(@PathVariable Long bookId, @PathVariable Long userId) {
        return ApiResponse.<Boolean>builder()
                .data(borrowService.borrowBook(bookId, userId))
                .build();
    }

    @PutMapping("/return/{userId}")
    public ApiResponse<Boolean> returnBook(@RequestBody List<Long> ids,@PathVariable Long userId) {
        return ApiResponse.<Boolean>builder()
                .data(borrowService.returnBooks(ids, userId))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<BorrowResponse>> getBorrowByState(@RequestParam() Long userId,
                                                              @RequestParam(defaultValue = "BORROWED") String status
                                                              ){
        return ApiResponse.<List<BorrowResponse>>builder()
                .data(borrowService.findByUserId(userId, status))
                .build();
    }

    @GetMapping("/check")
    public ApiResponse<Boolean> checkBorrowed(@RequestParam Long userId, @RequestParam Long bookId){
        return ApiResponse.<Boolean>builder()
                .data(borrowService.check(userId, bookId))
                .build();
    }
}
