package com.library.book_service.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NewBookRequest {
    Long number;
    Long id;
    String bookCode;
    String name;
    LocalDate publicationDate;
    String edition;
    Long numberPage;
    String shortDescription;
    Long price;
    String author;
    Object image;
    String language;
    List<CategoryRequest> categories;
}
