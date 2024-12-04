package com.library.message_service.services.impl;

import com.library.message_service.dtos.requests.ChatRequest;
import com.library.message_service.dtos.responses.ChatResponse;
import com.library.message_service.entities.Chat;
import com.library.message_service.exceptions.AppException;
import com.library.message_service.exceptions.ErrorCode;
import com.library.message_service.repositories.ChatRepo;
import com.library.message_service.services.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    ChatRepo repo;


    @Override
    public List<ChatResponse> getAll(Long userId) {
        List<Chat> chatList = repo.findByUserId1(userId);
        chatList.addAll(repo.findByUserId2(userId));
        List<ChatResponse> chatResponseList = new ArrayList<>();
        for (Chat chat : chatList) {
            chatResponseList.add(toChatResponse(chat));
        }
        return chatResponseList;
    }

    @Override
    public Chat getChat(Long id) throws AppException {
        Optional<Chat> chat = repo.findById(id);
        if(chat.isPresent()) return chat.get();
        throw new AppException(ErrorCode.INVALID_INPUT);
    }

    @Override
    public ChatResponse getChatById(Long id) throws AppException {
        Optional<Chat> chat = repo.findById(id);
        if(chat.isPresent()) return toChatResponse(chat.get());
        throw new AppException(ErrorCode.INVALID_INPUT);
    }

    @Override
    public ChatResponse getChat(Long id1, Long id2) throws AppException {
        String chatId1 = toChatId(id1, id2);
        Optional<Chat> chat = repo.findByChatId(chatId1);
        if(chat.isPresent()){
            return toChatResponse(chat.get());
        }

        else{
            chat = repo.findByChatId(toChatId(id2, id1));
            if(chat.isPresent()){
                return toChatResponse(chat.get());
            }
            else{
                Chat createChat = Chat.builder()
                        .userId1(id1)
                        .userId2(id2)
                        .chatId(toChatId(id1, id2))
                        .build();
                return toChatResponse(repo.save(createChat));
            }
        }
    }

    @Override
    public String toChatId(Long id1, Long id2){
        return id1.toString() + "_" + id2.toString();
    }

    @Override
    public Chat toChat(ChatRequest request){
        return  Chat.builder()
                .userId1(request.getUserId1())
                .userId2(request.getUserId2())
                .chatId(request.getChatId())
                .id(request.getId())
                .build();
    }

    @Override
    public ChatResponse toChatResponse(Chat request){
        return  ChatResponse.builder()
                .chatId(request.getChatId())
                .userId1(request.getUserId1())
                .userId2(request.getUserId2())
                .id(request.getId())
                .build();
    }}
