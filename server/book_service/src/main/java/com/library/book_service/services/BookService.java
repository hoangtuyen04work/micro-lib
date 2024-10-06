package com.library.book_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.requests.BookRequest;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.PageResponse;
import com.library.book_service.entities.Book;
import com.library.book_service.exceptions.AppException;

import java.util.List;

public interface BookService {
    List<BookResponse> getAll() throws JsonProcessingException;

    PageResponse<BookResponse> search(String name, Integer size, Integer page) throws JsonProcessingException;

    Boolean returnBook(List<Long> bookIds);

    String returnBook(Long id);

    Boolean borrow(List<Long> ids);

    String borrow(Long id) throws AppException, JsonProcessingException;

    List<Long> getNumbers(List<Long> ids) throws JsonProcessingException;

    Long getNumberById(Long id) throws JsonProcessingException;

    List<BookResponse> getById(List<Long> id) throws JsonProcessingException, AppException;

    BookResponse getById(Long id) throws JsonProcessingException, AppException;

    BookResponse updateBook(Long id, NewBookRequest updated);

    void deleteBook(Long id);

    BookResponse createBook(NewBookRequest request);

    List<BookResponse> toBookResponses(List<Book> books);

    Book toBook(NewBookRequest request);

    Book toBook(BookRequest request);

    BookResponse toBookResponse(Book request);
}
