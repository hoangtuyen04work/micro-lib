package com.library.book_service.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.BookResponseSimple;
import com.library.book_service.dtos.responses.PageResponse;
import com.library.book_service.services.BookRedisService;
import com.library.book_service.services.RedisCacheService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookRedisServiceImpl implements BookRedisService {
    RedisCacheService redisCacheService;

    @Override
    public PageResponse<BookResponseSimple> getTop(Integer typeId, Integer size, Integer page) throws JsonProcessingException {
        String key = "getTop:" + "typeId_" + typeId + " size:" + size + " page:" + page;
        return  redisCacheService.get(key);
    }

    @Override
    public void saveGetTop(Integer typeId, Integer size, Integer page, PageResponse<BookResponseSimple> response) throws JsonProcessingException {
        String key = "getTop:" + "typeId_" + typeId + " size:" + size + " page:" + page;
        redisCacheService.save(key, response);
    }

    @Override
    public List<BookResponse> getAll() throws JsonProcessingException {
        String key = "getAll";
        return redisCacheService.get(key);
    }

    @Override
    public void saveGetAll(List<BookResponse> response) throws JsonProcessingException {
        String key = "getAll";
        redisCacheService.save(key, response);
    }

    @Override
    public PageResponse<BookResponseSimple> search(String name, Integer size, Integer page) throws JsonProcessingException {
        String key = "search_book" + name + size + page;
        return redisCacheService.get(key);
    }

    @Override
    public void saveSearch(String name, Integer size, Integer page, PageResponse<BookResponseSimple> response) throws JsonProcessingException {
        String key = "search_book" + name + size + page;
        redisCacheService.save(key, response);
    }

    @Override
    public List<Long> getNumbers(List<Long> ids) throws JsonProcessingException {
        StringBuilder key = new StringBuilder("get_numbers");
        for(Long id : ids){
            key.append(id);
        }
        return redisCacheService.get(key.toString());
    }

    @Override
    public void saveGetNumbers(List<Long> ids,List<Long> numbers) throws JsonProcessingException {
        StringBuilder key = new StringBuilder("get_numbers");
        for(Long id : ids){
            key.append(id);
        }
        redisCacheService.save(key.toString(), numbers);
    }

    @Override
    public Long getNumberById(Long id) throws JsonProcessingException {
        String key = "get_number:" + id;
        return  redisCacheService.get(key);
    }

    @Override
    public void saveGetNumberById(Long id,Long number) throws JsonProcessingException {
        String key = "get_number:" + id;
        redisCacheService.save(key, number);
    }

    @Override
    public BookResponse get(Long id) throws JsonProcessingException {
        String key = "get_book:" + id;
        return redisCacheService.get(key);
    }

    @Override
    public void saveGetBook(Long id, BookResponse response) throws JsonProcessingException {
        String key = "get_book:" + id;
        redisCacheService.save(key, response);
    }

    @Override
    public List<BookResponse> get(List<Long> id) throws JsonProcessingException {
        String key = getKeyForm(id);
        return redisCacheService.get(key);
    }

    @Override
    public void saveGetBook(List<Long> id, List<BookResponse> response) throws JsonProcessingException {
        String key = getKeyForm(id);
        redisCacheService.save(key, response);
    }

    @Override
    public void clear(){
        redisCacheService.clear();
    }

    @Override
    public String getKeyForm(List<Long> ids){
        StringBuilder stringBuilder = new StringBuilder("get_book:");
        for(Long id : ids){
            stringBuilder.append(id);
        }
        return stringBuilder.toString();
    }

}
