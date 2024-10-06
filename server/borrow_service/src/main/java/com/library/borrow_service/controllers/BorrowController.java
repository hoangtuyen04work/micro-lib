package com.library.borrow_service.controllers;

import com.library.borrow_service.dtos.ApiResponse;
import com.library.borrow_service.dtos.responses.BorrowResponse;
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


    @PutMapping("/borrow/{id}")
    public ApiResponse<Boolean> borrowBook(@RequestBody List<Long> ids, @PathVariable Long id) {
        return ApiResponse.<Boolean>builder()
                .data(borrowService.borrowBook(ids, id))
                .build();
    }

    @PutMapping("/return/{id}")
    public ApiResponse<Boolean> returnBook(@RequestBody List<Long> ids, Long id) {
        return ApiResponse.<Boolean>builder()
                .data(borrowService.returnBooks(ids, id))
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
}
