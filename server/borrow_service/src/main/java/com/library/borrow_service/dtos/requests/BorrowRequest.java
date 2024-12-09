package com.library.borrow_service.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BorrowRequest {
    Long id;
    Long bookId;
    Long userId;
    LocalDateTime borrowDate;
    LocalDateTime returnDate;
    String status;
}
