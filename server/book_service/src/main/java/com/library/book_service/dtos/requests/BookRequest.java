package com.library.book_service.dtos.requests;

import com.library.book_service.entities.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookRequest {
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
    Long number;
    List<CategoryRequest> categories;
}
