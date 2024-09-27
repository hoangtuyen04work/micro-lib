package com.library.book_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.ApiResponse;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.exceptions.AppException;
import com.library.book_service.services.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookController {
    BookService bookService;

    @PostMapping("/create")
    public ApiResponse<BookResponse> createBook(@RequestBody NewBookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.createBook(request))
                .build();
    }

    @GetMapping("/get")
    public ApiResponse<BookResponse> getBook(@RequestParam Long id) throws AppException, JsonProcessingException {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.getById(id))
                .build();
    }

    @PutMapping("/update")
    public ApiResponse<BookResponse> updateBook(@RequestParam Long id, @RequestBody NewBookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.updateBook(id, new NewBookRequest()))
                .build();
    }
}
