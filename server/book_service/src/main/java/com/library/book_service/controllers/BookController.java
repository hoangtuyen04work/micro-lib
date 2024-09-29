package com.library.book_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.ApiResponse;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.PageResponse;
import com.library.book_service.exceptions.AppException;
import com.library.book_service.services.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    BookService bookService;

    @GetMapping("/search")
    public ApiResponse<PageResponse<BookResponse>> getAllBooks(@RequestParam String name,
                                                               @RequestParam(defaultValue = "10") Long size,
                                                               @RequestParam(defaultValue = "0") Long page){
        return  ApiResponse.<PageResponse<BookResponse>>builder()
                .data(bookService.search(name, size, page))
                .build();
    }

    @PutMapping("/return")
    public ApiResponse<Boolean> returnBook(@RequestBody List<Long> bookIds){
        bookService.returnBook(bookIds);
        return ApiResponse.<Boolean>builder()
                .data(true)
                .build();
    }

    @PostMapping("/number")
    public ApiResponse<List<Long>> newBook(@RequestBody List<Long> ids) throws AppException, JsonProcessingException {
        return ApiResponse.<List<Long>>builder()
                .data(bookService.getNumbers(ids))
                .build();
    }

    @PostMapping("/borrow")
    public ApiResponse<Boolean> borrow(@RequestBody List<Long> id){
        bookService.borrow(id);
        return ApiResponse.<Boolean>builder()
                .data(true)
                .build();
    }

    @GetMapping("/number/{id}")
    public ApiResponse<Long> getNumberById(@PathVariable Long id) throws JsonProcessingException {
        return ApiResponse.<Long>builder()
                .data(bookService.getNumberById(id))
                .build();
    }

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
