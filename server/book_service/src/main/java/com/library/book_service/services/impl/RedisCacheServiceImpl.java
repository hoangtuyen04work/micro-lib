package com.library.book_service.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.book_service.services.RedisCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheServiceImpl implements RedisCacheService {
    ObjectMapper objectMapper;
    RedisTemplate<String, Object> redis;

    @Override
    public <T> T get(String key) throws JsonProcessingException {
        String json = (String)redis.opsForValue().get(key);
        return json == null ? null :
                objectMapper.readValue(json, new TypeReference<T>() {});
    }

    @Override
    public void save(String key, Object data) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(data);
        redis.opsForValue().set(key, json);
    }

    @Override
    public void clear(){
        redis.getConnectionFactory().getConnection().flushAll();
    }
}
