package com.library.notification.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NotifyResponse {
    Long id;
    Long userId;
    String title;
    Boolean send;
    String body;
    LocalDate date;
}
