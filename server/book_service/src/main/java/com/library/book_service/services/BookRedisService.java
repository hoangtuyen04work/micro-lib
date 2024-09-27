package com.library.book_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRedisService {
    BookResponse get(Long id) throws JsonProcessingException;

    void saveGetBook(Long id, BookResponse response) throws JsonProcessingException;

    List<BookResponse> get(List<Long> id) throws JsonProcessingException;

    void saveGetBook(List<Long> id, List<BookResponse> response) throws JsonProcessingException;

    void clear();

    String getKeyForm(List<Long> ids);
}