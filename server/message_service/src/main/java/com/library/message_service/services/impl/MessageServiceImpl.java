package com.library.message_service.services.impl;

import com.library.message_service.dtos.requests.MessageRequest;
import com.library.message_service.dtos.responses.MessageResponse;
import com.library.message_service.dtos.responses.PageResponse;
import com.library.message_service.entities.Message;
import com.library.message_service.exceptions.AppException;
import com.library.message_service.repositories.MessageRepo;
import com.library.message_service.services.ChatService;
import com.library.message_service.services.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    MessageRepo repo;
    ChatService chatService;

    @Override
    public void deleteMessage(Long id) throws AppException {
        repo.deleteById(id);
    }

    @Override
    public MessageResponse sendMessage(MessageRequest request) throws AppException {
        Message message = Message.builder()
                .sender(request.getSender())
                .content(request.getContent())
                .chat(chatService.getChat(request.getChatId()))
                .build();
        return toMessageResponse(repo.save(message));
    }

    @Override
    public PageResponse<MessageResponse> getMessages(Long id, Long page, Long size) throws AppException {
        Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(size));
        Page<Message> messages = repo.findByChat(chatService.getChat(id), pageable);
        return PageResponse.<MessageResponse>builder()
                .content(messages.getContent().stream().map(this::toMessageResponse).toList())
                .totalPages(messages.getTotalPages())
                .totalElements(messages.getTotalElements())
                .pageNumber(messages.getNumber())
                .pageSize(messages.getSize())
                .build();
    }

    @Override
    public MessageResponse toMessageResponse(Message request){
        return  MessageResponse.builder()
                .id(request.getId())
                .content(request.getContent())
                .sender(request.getSender())
                .build();
    }

    @Override
    public Message toMessage(MessageRequest request){
        return  Message.builder()
                .id(request.getId())
                .content(request.getContent())
                .sender(request.getSender())
                .build();
    }
}
