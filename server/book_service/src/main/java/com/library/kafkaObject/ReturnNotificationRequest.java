package com.library.kafkaObject;

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
public class ReturnNotificationRequest {
    List<String> bookName;
    Long userId;
    LocalDate borrowTime;
}
