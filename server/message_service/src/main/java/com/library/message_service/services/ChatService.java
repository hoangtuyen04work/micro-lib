package com.library.message_service.services;

import com.library.message_service.dtos.requests.ChatRequest;
import com.library.message_service.dtos.responses.ChatResponse;
import com.library.message_service.entities.Chat;
import com.library.message_service.exceptions.AppException;

import java.util.List;

public interface ChatService {
    List<ChatResponse> getAll(Long userId);


    Chat getChat(Long id) throws AppException;

    ChatResponse getChatById(Long id) throws AppException;

    ChatResponse getChat(Long id1, Long id2) throws AppException;

    String toChatId(Long id1, Long id2);

    Chat toChat(ChatRequest request);

    ChatResponse toChatResponse(Chat request);
}
