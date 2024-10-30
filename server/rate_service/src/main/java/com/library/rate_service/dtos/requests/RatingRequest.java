package com.library.rate_service.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class RatingRequest {

    Long userId;

    String userName;

    Long bookId;

    Integer rating;

    String comment;
}