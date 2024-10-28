package com.library.book_service.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.book_service.dtos.responses.BookResponse;
import com.library.book_service.dtos.responses.BookResponseSimple;
import com.library.book_service.dtos.responses.PageResponse;
import com.library.book_service.services.BookRedisService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookRedisServiceImpl implements BookRedisService {
    RedisTemplate<String, Object> redisTemplate;
    ObjectMapper objectMapper;


    @Override
    public PageResponse<BookResponseSimple> getTop(Integer typeId, Integer size, Integer page) throws JsonProcessingException {
        String key = "getTop:" + "typeId_" + typeId + " size:" + size + " page:" + page;
        String json = (String)redisTemplate.opsForValue().get(key);
        return json == null ?
                null
                :
                objectMapper.readValue(json, new TypeReference<PageResponse<BookResponseSimple>>(){});
    }

    @Override
    public void saveGetTop(Integer typeId, Integer size, Integer page, PageResponse<BookResponseSimple> response) throws JsonProcessingException {
        String key = "getTop:" + "typeId_" + typeId + " size:" + size + " page:" + page;
        String json = objectMapper.writeValueAsString(response);
        redisTemplate.opsForValue().set(String.valueOf(key), json);
    }

    @Override
    public List<BookResponse> getAll() throws JsonProcessingException {
        String key = new String("getAll");
        String json = (String)redisTemplate.opsForValue().get(key);
        return json == null ?
                null
                :
                objectMapper.readValue(json, new TypeReference<List<BookResponse>>(){});
    }

    @Override
    public void saveGetAll(List<BookResponse> response) throws JsonProcessingException {
        StringBuilder key = new StringBuilder("getAll");
        String json = objectMapper.writeValueAsString(response);
        redisTemplate.opsForValue().set(String.valueOf(key), json);
    }

    @Override
    public PageResponse<BookResponseSimple> search(String name, Integer size, Integer page) throws JsonProcessingException {
        String key = "search_book" + name + size + page;

        String json = (String)redisTemplate.opsForValue().get(key);
        return json == null ?
                null
                :
                objectMapper.readValue(json, new TypeReference<PageResponse<BookResponseSimple>>() {});
    }

    @Override
    public void saveSearch(String name, Integer size, Integer page, PageResponse<BookResponseSimple> response) throws JsonProcessingException {
        String key = "search_book" + name + size + page;
        String json = objectMapper.writeValueAsString(response);
        redisTemplate.opsForValue().set(String.valueOf(key), json);
    }

    @Override
    public List<Long> getNumbers(List<Long> ids) throws JsonProcessingException {
        StringBuilder key = new StringBuilder("get_numbers");
        for(Long id : ids){
            key.append(id);
        }
        String json = (String)redisTemplate.opsForValue().get(key.toString());
        return json == null ?
                null
                :
                objectMapper.readValue(json, new TypeReference<List<Long>>() {});
    }

    @Override
    public void saveGetNumbers(List<Long> ids,List<Long> numbers) throws JsonProcessingException {
        StringBuilder key = new StringBuilder("get_numbers");
        for(Long id : ids){
            key.append(id);
        }
        String json = objectMapper.writeValueAsString(numbers);
        redisTemplate.opsForValue().set(String.valueOf(key), json);
    }

    @Override
    public Long getNumberById(Long id) throws JsonProcessingException {
        String key = "get_number:" + id;
        String json = (String)redisTemplate.opsForValue().get(key);
        return json == null ?
                null
                :
                objectMapper.readValue(json, new TypeReference<Long>() {});
    }

    @Override
    public void saveGetNumberById(Long id,Long number) throws JsonProcessingException {
        String key = "get_number:" + id;
        String json = objectMapper.writeValueAsString(number);
        redisTemplate.opsForValue().set(key, json);
    }

    @Override
    public BookResponse get(Long id) throws JsonProcessingException {
        String key = "get_book:" + id;
        String json = (String)redisTemplate.opsForValue().get(key);
        return json == null ?
                null
                :
                objectMapper.readValue(json, new TypeReference<BookResponse>() {});
    }

    @Override
    public void saveGetBook(Long id, BookResponse response) throws JsonProcessingException {
        String key = "get_book:" + id;
        String json = objectMapper.writeValueAsString(response);
        redisTemplate.opsForValue().set(key, json);
    }

    @Override
    public List<BookResponse> get(List<Long> id) throws JsonProcessingException {
        String key = getKeyForm(id);
        String json = (String)redisTemplate.opsForValue().get(key);
        return json == null ?
                null
                :
                objectMapper.readValue(json, new TypeReference<List<BookResponse>>() {});
    }

    @Override
    public void saveGetBook(List<Long> id, List<BookResponse> response) throws JsonProcessingException {
        String key = getKeyForm(id);
        String json = objectMapper.writeValueAsString(response);
        redisTemplate.opsForValue().set(key, json);
    }

    @Override
    public void clear(){
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    public String getKeyForm(List<Long> ids){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("get_book:");
        for(Long id : ids){
            stringBuilder.append(id);
        }
        return stringBuilder.toString();
    }

}
