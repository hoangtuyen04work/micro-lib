package com.library.book_service.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.book_service.services.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redis;

    @Autowired
    public RedisCacheServiceImpl(ObjectMapper objectMapper, RedisTemplate<String, Object> redis) {
        this.objectMapper = objectMapper;
        this.redis = redis;
    }

    @Override
    public <T> T get(String key) throws JsonProcessingException {
        String json = (String) redis.opsForValue().get(key);
        return json == null ? null : objectMapper.readValue(json, new TypeReference<T>() {});
    }

    @Override
    public <T> void save(String key, T data) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(data);
        redis.opsForValue().set(String.valueOf(key), json);
    }

    @Override
    public void clear() {
        redis.getConnectionFactory().getConnection().flushAll();
    }
}
