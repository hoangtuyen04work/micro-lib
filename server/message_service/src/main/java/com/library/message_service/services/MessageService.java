package com.library.message_service.services;

import com.library.message_service.dtos.requests.MessageRequest;
import com.library.message_service.dtos.responses.MessageResponse;
import com.library.message_service.dtos.responses.PageResponse;
import com.library.message_service.entities.Message;
import com.library.message_service.exceptions.AppException;

import java.util.List;

public interface MessageService{
    void deleteMessage(Long id) throws AppException;

    MessageResponse sendMessage(MessageRequest request) throws AppException;

    PageResponse<MessageResponse> getMessages(Long id, Long page, Long size) throws AppException;


    MessageResponse toMessageResponse(Message request);

    Message toMessage(MessageRequest request);
}
