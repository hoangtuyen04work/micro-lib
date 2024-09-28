package com.library.book_service.entities;

import com.library.book_service.services.BookRedisService;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class BookListener {
    BookRedisService bookRedisService;

    @PrePersist
    public void prePersist(Book product) {
    }
    @PostPersist
    public void postPersist(Book product) {
         bookRedisService.clear();
    }
    @PreUpdate
    public void preUpdate(Book product) {
    }
    @PostUpdate
    public void postUpdate(Book product) {
        bookRedisService.clear();
    }
    @PreRemove
    public void preRemove(Book product) {
    }
    @PostRemove
    public void postRemove(Book product){
        bookRedisService.clear();
    }
}
