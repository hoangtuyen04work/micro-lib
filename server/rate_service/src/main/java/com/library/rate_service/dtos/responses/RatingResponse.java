package com.library.rate_service.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class RatingResponse {

    Long id;

    Long userId;

    Long bookId;

    Integer rating;

    String comment;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
