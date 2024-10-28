package com.library.search_service.dtos.responses;

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
public class BookResponse {
    Long number;
    Long id;
    String bookCode;
    Long numberBorrowed;
    String name;
    LocalDate publicationDate;
    String edition;
    Long numberPage;
    String shortDescription;
    Long price;
    String author;
    String imageUrl;
    String language;
    List<CategoryResponse> categories;
}
