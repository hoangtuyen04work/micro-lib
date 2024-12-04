package com.library.message_service.controllers;

import com.library.message_service.dtos.ApiResponse;
import com.library.message_service.dtos.responses.ChatResponse;
import com.library.message_service.exceptions.AppException;
import com.library.message_service.services.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {
    SimpMessagingTemplate simpMessagingTemplate;
    ChatService chatService;

    @MessageMapping("/createChat")
    public void getChat(@RequestParam Long id1, @RequestParam Long id2) throws AppException {
        ChatResponse response = chatService.getChat(id1, id2);
        simpMessagingTemplate.convertAndSend("/topic/receive/" + response);
    }

    @GetMapping("/getAll")
    public ApiResponse<List<ChatResponse>> getAll(Long userId){
        return ApiResponse.<List<ChatResponse>>builder()
                .data(chatService.getAll(userId))
                .build();
    }


}
