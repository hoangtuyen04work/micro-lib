package com.library.message_service.dtos.requests;


import lombok.*;
import lombok.experimental.FieldDefaults;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRequest {
    Long chatId;
    Long sender;
    String content;
    Long id;
}
