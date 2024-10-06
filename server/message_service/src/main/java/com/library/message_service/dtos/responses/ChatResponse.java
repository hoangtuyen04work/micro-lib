package com.library.message_service.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatResponse {
    Long id;
    Long userId1;
    Long userId2;
    String chatId;

}