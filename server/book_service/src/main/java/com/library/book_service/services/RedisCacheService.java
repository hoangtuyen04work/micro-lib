package com.library.book_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RedisCacheService {
    <T> T get(String key) throws JsonProcessingException;

    <T>void save(String key, T data) throws JsonProcessingException;

    void clear();
}
