package com.library.book_service.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NewBookRequest {
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
    MultipartFile image;
    String language;
    List<CategoryRequest> categories;
}
