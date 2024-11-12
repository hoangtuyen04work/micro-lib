package com.library.comment_service.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {

    Long id;

    Long userId;

    Long bookId;

    String content;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    Integer likes;
}
