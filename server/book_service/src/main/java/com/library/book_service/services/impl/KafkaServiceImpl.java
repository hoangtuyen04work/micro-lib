package com.library.book_service.services.impl;

import com.library.book_service.services.KafkaService;
import com.library.kafkaObject.BorrowNotificationRequest;
import com.library.kafkaObject.ReturnNotificationRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaServiceImpl  implements KafkaService {
    KafkaTemplate<String, ReturnNotificationRequest> kafkaReturn;
    KafkaTemplate<String, BorrowNotificationRequest> kafkaBorrow;
    @NonFinal
    @Value("${kafka.return}")
    String returnTopic;
    @Value("${kafka.borrow}")
    @NonFinal
    String borrowTopic;

    @Override
    public void borrowNotify(Long bookId, Long userId, String bookName){
        kafkaBorrow.send(borrowTopic, BorrowNotificationRequest.builder()
                .bookName(List.of(bookName))
                .userId(userId)
                .borrowTime(LocalDate.now())
                .build());
    }

    @Override
    public void returnNotify(Long bookId, Long userId, String bookName){
        kafkaReturn.send(returnTopic, ReturnNotificationRequest.builder()
                .bookName(List.of(bookName))
                .userId(userId)
                .borrowTime(LocalDate.now())
                .build());
    }
}
