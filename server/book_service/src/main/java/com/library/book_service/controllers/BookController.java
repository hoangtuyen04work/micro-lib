package com.library.book_service.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.ApiResponse;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.BookResponseSimple;
import com.library.book_service.dtos.responses.BookSimpleResponse;
import com.library.book_service.dtos.responses.PageResponse;
import com.library.book_service.exceptions.AppException;
import com.library.book_service.services.BookService;
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
    BookService bookService;

    //get basic information of a book
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/basic")
    public ApiResponse<PageResponse<BookSimpleResponse>> getTopBasic(@RequestParam(defaultValue = "0") Integer page,
                                                                              @RequestParam(defaultValue = "10")Integer size) {
        return ApiResponse.<PageResponse<BookSimpleResponse>>builder()
                .data(bookService.getBookSimpleResponse(page, size))
                .build();
    }

    // get list book recently borrowed
    @GetMapping("/getTop")
    public ApiResponse<PageResponse<BookResponse>> getTop(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10")Integer size) {
        return ApiResponse.<PageResponse<BookResponse>>builder()
                .data(bookService.getTopBorrow(page, size))
                .build();
    }


    @GetMapping("/search")
    public ApiResponse<PageResponse<BookResponseSimple>> getAllBooks(@RequestParam String name,
                                                               @RequestParam(defaultValue = "10") Integer size,
                                                               @RequestParam(defaultValue = "0") Integer page) throws JsonProcessingException {
        return  ApiResponse.<PageResponse<BookResponseSimple>>builder()
                .data(bookService.search(name, size, page))
                .build();
    }

    @PutMapping("/returns/{userId}")
    public ApiResponse<Boolean> returnBooks(@RequestBody List<Long> bookIds, @PathVariable Long userId) throws AppException {
        bookService.returnBook(bookIds, userId);
        return ApiResponse.<Boolean>builder()
                .data(true)
                .build();
    }

    @PutMapping("/return/{userId}")
    public ApiResponse<Boolean> returnBook(@RequestBody Long bookId, @PathVariable Long userId) throws AppException {
        bookService.returnBook(bookId, userId);
        return ApiResponse.<Boolean>builder()
                .data(true)
                .build();
    }

    @PostMapping("/number")
    public ApiResponse<List<Long>> newBook(@RequestBody List<Long> ids) throws JsonProcessingException, AppException {
        return ApiResponse.<List<Long>>builder()
                .data(bookService.getNumbers(ids))
                .build();
    }

    @PostMapping("/borrows/{userId}")
    public ApiResponse<Boolean> borrow(@RequestBody List<Long> id, @PathVariable Long userId) throws AppException {
        bookService.borrow(id, userId);
        return ApiResponse.<Boolean>builder()
                .data(true)
                .build();
    }

    @PostMapping("/borrow/{userId}")
    public ApiResponse<Boolean> borrow(@RequestBody Long id, @PathVariable Long userId) throws AppException, JsonProcessingException {
        bookService.borrow(id, userId);
        return ApiResponse.<Boolean>builder()
                .data(true)
                .build();
    }

    @GetMapping("/number/{id}")
    public ApiResponse<Long> getNumberById(@PathVariable Long id) throws JsonProcessingException, AppException {
        return ApiResponse.<Long>builder()
                .data(bookService.getNumberById(id))
                .build();
    }



    @GetMapping("/{id}")
    public ApiResponse<BookResponse> getBook(@PathVariable Long id) throws AppException, JsonProcessingException {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.getById(id))
                .build();
    }

    @GetMapping("/gets")
    public ApiResponse<List<BookResponse>> getBook(@RequestParam List<Long> bookIds) {
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.getBooks(bookIds))
                .build();
    }

    @GetMapping("/getAll")
    public ApiResponse<List<BookResponse>> getALlBook(@RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer size,
                                                      @RequestParam(required = false, defaultValue = "id") String sort
                                                      ) throws JsonProcessingException {
        return ApiResponse.<List<BookResponse>>builder()
                .data(bookService.getAll(page, size, sort))
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ApiResponse<BookResponse> createBook(@ModelAttribute NewBookRequest request) {
        return ApiResponse.<BookResponse>builder()
                .data(bookService.createBook(request))
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update")
    public ApiResponse<BookResponse> updateBook(@RequestParam Long id, @ModelAttribute NewBookRequest request) throws AppException {
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
