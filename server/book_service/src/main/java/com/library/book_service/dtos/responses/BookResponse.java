package com.library.book_service.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
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
    String name;
    Long numberBorrowed;
    LocalDate publicationDate;
    String edition;
    Long numberPage;
    String shortDescription;
    Long price;
    String author;
    String image;
    String language;
    List<CategoryResponse> categories;
}
