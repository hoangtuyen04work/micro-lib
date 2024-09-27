package com.library.book_service.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CategoryRequest {
    Long id;
    String category;
}
