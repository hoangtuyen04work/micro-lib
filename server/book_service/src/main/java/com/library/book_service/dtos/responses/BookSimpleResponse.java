package com.library.book_service.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookSimpleResponse {
    Long numberBorrowed;
    Long id;
    String name;
    Long price;
    String author;
    String language;
}

