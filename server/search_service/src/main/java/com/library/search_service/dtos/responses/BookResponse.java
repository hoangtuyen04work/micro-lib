package com.library.search_service.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookResponse {
    Long number;
    Long id;
    String bookCode;
    String name;
    Date publicationDate;
    String edition;
    Long numberPage;
    String shortDescription;
    Long price;
    String author;
    String imageUrl;
    String language;
    List<CategoryResponse> categories;
}
