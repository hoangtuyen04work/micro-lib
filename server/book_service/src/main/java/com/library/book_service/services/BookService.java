package com.library.book_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.BookResponseSimple;
import com.library.book_service.dtos.responses.PageResponse;
import com.library.book_service.entities.Book;
import com.library.book_service.exceptions.AppException;

import java.util.List;
import java.util.Optional;

public interface BookService {
    PageResponse<BookResponse> getTopBorrow(Integer page, Integer size);

    List<BookResponse> getBooks(List<Long> bookIds);

    PageResponse<BookResponseSimple> getTop(Integer size, Integer page, Integer typeId) throws JsonProcessingException;

    List<BookResponse> getAll(Integer page, Integer size, String sort) throws JsonProcessingException;

    PageResponse<BookResponseSimple> search(String name, Integer size, Integer page) throws JsonProcessingException;

    void returnBook(List<Long> bookIds, Long userId) throws AppException;

    void returnBook(Long id, Long userId) throws AppException;

    void borrow(List<Long> ids, Long userId) throws AppException;

    void borrow(Long id, Long userId) throws AppException, JsonProcessingException;

    List<Long> getNumbers(List<Long> ids) throws JsonProcessingException, AppException;

    Long getNumberById(Long id) throws JsonProcessingException, AppException;

    Book findById(Long id) throws AppException;

    List<BookResponse> getByIds(List<Long> ids) throws JsonProcessingException, AppException;

    BookResponse getById(Long id) throws JsonProcessingException, AppException;

    BookResponse updateBook(Long id, NewBookRequest updated) throws AppException;

    void deleteBook(Long id);

    BookResponse createBook(NewBookRequest request);

    Book checkOptional(Optional<Book> book) throws AppException;
}
