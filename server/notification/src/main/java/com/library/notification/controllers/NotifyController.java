package com.library.notification.controllers;

import com.library.notification.dtos.ApiResponse;
import com.library.kafkaObject.BorrowNotificationRequest;
import com.library.kafkaObject.ReturnNotificationRequest;
import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.services.impl.BorrowNotifyServiceImpl;
import com.library.notification.services.impl.ReturnNotifyServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController()
@RequiredArgsConstructor
public class NotifyController {
    SimpMessagingTemplate simpMessagingTemplate;
    BorrowNotifyServiceImpl borrowNotifyService;
    ReturnNotifyServiceImpl returnNotifyService;
    @Value("${websocket.sendTo}")
    @NonFinal
    String sendTo;

    @KafkaListener(topics = "${kafka.return}", groupId = "xxx")
    public void returnNotify(@Payload ReturnNotificationRequest request) {
        System.err.println(request.getUserId());
        simpMessagingTemplate.convertAndSend(sendTo + "/" + request.getUserId().toString(),
                                            ApiResponse.<NotifyResponse>builder()
                .data(returnNotifyService.returnNotify(request))
                .build());
    }

    @KafkaListener(topics = "${kafka.borrow}", groupId = "yyy")
    public void borrowNotify(@Payload BorrowNotificationRequest request) {
        System.err.println(request.getUserId());
        simpMessagingTemplate.convertAndSend(sendTo + "/" + request.getUserId().toString(),
                                            ApiResponse.<NotifyResponse>builder()
                .data(borrowNotifyService.borrowNotify(request))
                .build());
    }

}
