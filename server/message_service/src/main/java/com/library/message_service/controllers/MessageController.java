package com.library.message_service.controllers;

import com.library.message_service.dtos.requests.MessageRequest;
import com.library.message_service.dtos.responses.ChatResponse;
import com.library.message_service.dtos.responses.MessageResponse;
import com.library.message_service.exceptions.AppException;
import com.library.message_service.services.ChatService;
import com.library.message_service.services.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {
    MessageService messageService;
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send")
    public void sendMessage(@Payload MessageRequest request) throws AppException {
        MessageResponse response = messageService.sendMessage(request);
        simpMessagingTemplate.convertAndSend("/topic/receive/" + response);
    }

    @MessageMapping("/delete")
    public void deleteMessage(@Payload Long id) throws AppException {
        messageService.deleteMessage(id);
    }
}
