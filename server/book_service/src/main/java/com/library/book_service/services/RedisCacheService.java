package com.library.book_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RedisCacheService {
    <T> T get(String key) throws JsonProcessingException;

    void save(String key, Object data) throws JsonProcessingException;

    void clear();
}
