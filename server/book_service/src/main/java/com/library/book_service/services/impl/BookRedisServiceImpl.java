package com.library.book_service.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.book_service.dtos.responses.BookResponse;
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
