package com.library.borrow_service.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BorrowResponse {
    Long id;
    Long bookId;
    Long userId;
    LocalDate borrowDate;
    LocalDate returnDate;
    String status;
}
