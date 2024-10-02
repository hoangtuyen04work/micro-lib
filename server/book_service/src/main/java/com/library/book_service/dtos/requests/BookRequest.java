package com.library.book_service.dtos.requests;

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
public class BookRequest {
    Long id;
    String bookCode;
    String name;
    LocalDate publicationDate;
    String edition;
    Long numberPage;
    String shortDescription;
    Long price;
    String author;
    String imageUrl;
    String language;
    Long number;
    List<CategoryRequest> categories;
}
