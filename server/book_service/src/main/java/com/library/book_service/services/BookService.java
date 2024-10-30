package com.library.book_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.requests.BookRequest;
import com.library.book_service.dtos.requests.NewBookRequest;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.BookResponseSimple;
import com.library.book_service.dtos.responses.PageResponse;
import com.library.book_service.entities.Book;
import com.library.book_service.exceptions.AppException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    PageResponse<BookResponseSimple> getTop(Integer size, Integer page, Integer typeId) throws JsonProcessingException;

    List<BookResponse> getAll() throws JsonProcessingException;

    PageResponse<BookResponseSimple> search(String name, Integer size, Integer page) throws JsonProcessingException;

    Boolean returnBook(List<Long> bookIds, Long userId);

    String returnBook(Long id, Long userId);

    Boolean borrow(List<Long> ids, Long userId);

    String borrow(Long id, Long userId) throws AppException, JsonProcessingException;

    List<Long> getNumbers(List<Long> ids) throws JsonProcessingException;

    Long getNumberById(Long id) throws JsonProcessingException;

    List<BookResponse> getById(List<Long> id) throws JsonProcessingException, AppException;

    BookResponse getById(Long id) throws JsonProcessingException, AppException;

    BookResponse updateBook(Long id, NewBookRequest updated);

    void deleteBook(Long id);

    BookResponse createBook(NewBookRequest request);

}
