package com.library.book_service.services;

public interface KafkaService {

    void borrowNotify(Long bookId, Long userId, String bookName);

    void returnNotify(Long bookId, Long userId, String bookName);
}
