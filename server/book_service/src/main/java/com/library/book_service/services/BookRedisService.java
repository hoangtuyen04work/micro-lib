package com.library.book_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.BookResponseSimple;
import com.library.book_service.dtos.responses.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRedisService {

    PageResponse<BookResponseSimple> getTop(Integer typeId, Integer size, Integer page) throws JsonProcessingException;

    void saveGetTop(Integer typeId, Integer size, Integer page, PageResponse<BookResponseSimple> response) throws JsonProcessingException;

    List<BookResponse> getAll() throws JsonProcessingException;

    void saveGetAll(List<BookResponse> response) throws JsonProcessingException;

    PageResponse<BookResponseSimple> search(String name, Integer size, Integer page) throws JsonProcessingException;

    void saveGetNumbers(List<Long> ids, List<Long> numbers) throws JsonProcessingException;

    Long getNumberById(Long id) throws JsonProcessingException;

    void saveSearch(String name, Integer size, Integer page, PageResponse<BookResponseSimple> response) throws JsonProcessingException;

    List<Long> getNumbers(List<Long> ids) throws JsonProcessingException;

    void saveGetNumberById(Long id, Long number) throws JsonProcessingException;

    BookResponse get(Long id) throws JsonProcessingException;

    void saveGetBook(Long id, BookResponse response) throws JsonProcessingException;

    List<BookResponse> get(List<Long> id) throws JsonProcessingException;

    void saveGetBook(List<Long> id, List<BookResponse> response) throws JsonProcessingException;

    void clear();

    String getKeyForm(List<Long> ids);
}
