package com.library.book_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.ApiResponse;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.BookResponseSimple;
import com.library.book_service.dtos.responses.PageResponse;
import com.library.book_service.exceptions.AppException;
import com.library.book_service.services.impl.BookServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    BookServiceImpl bookService;

    @GetMapping("/search")
    public ApiResponse<PageResponse<BookResponseSimple>> getAllBooks(@RequestParam String name,
                                                               @RequestParam(defaultValue = "10") Integer size,
                                                               @RequestParam(defaultValue = "0") Integer page) throws JsonProcessingException {
        return  ApiResponse.<PageResponse<BookResponseSimple>>builder()
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
    public ApiResponse<List<Long>> newBook(@RequestBody List<Long> ids) throws JsonProcessingException {
        return ApiResponse.<List<Long>>builder()
                .data(bookService.getNumbers(ids))
                .build();
    }

    @PostMapping("/borrows")
    public ApiResponse<Boolean> borrow(@RequestBody List<Long> id){
        bookService.borrow(id);
        return ApiResponse.<Boolean>builder()
                .data(true)
                .build();
    }

    @PostMapping("/borrow")
    public ApiResponse<Boolean> borrow(@RequestBody Long id){
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ApiResponse<BookResponse> createBook(@ModelAttribute NewBookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.createBook(request))
                .build();
    }

    @GetMapping()
    public ApiResponse<BookResponse> getBook(@RequestParam Long id) throws AppException, JsonProcessingException {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.getById(id))
                .build();
    }

    @GetMapping("/getAll")
    public ApiResponse<List<BookResponse>> getALlBook() throws AppException, JsonProcessingException {
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.getAll())
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update")
    public ApiResponse<BookResponse> updateBook(@RequestParam Long id, @RequestBody NewBookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.updateBook(id, request))
                .build();
    }

    @GetMapping("/top")
    public ApiResponse<PageResponse<BookResponseSimple>> topAll(@RequestParam(defaultValue = "10") Integer size,
                                                        @RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "0") Integer typeId) throws JsonProcessingException {
        return ApiResponse.<PageResponse<BookResponseSimple>>builder()
                .data(bookService.getTop(size, page, typeId))
                .build();
    }
}
