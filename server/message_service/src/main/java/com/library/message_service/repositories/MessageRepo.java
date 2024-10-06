package com.library.message_service.repositories;


import com.library.message_service.entities.Chat;
import com.library.message_service.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    Page<Message> findByChat(Chat chat, Pageable pageable);
}
