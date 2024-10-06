package com.library.message_service.repositories;


import com.library.message_service.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChatId(String chatId);
    List<Chat> findByUserId1(Long userId1);
    List<Chat> findByUserId2(Long userId2);
}
