package com.library.book_service.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookResponseSimple {
    Long id;
    String name;
    String image;
    Long numberBorrowed;
}
