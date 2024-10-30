package com.library.notification.services.impl;

import com.library.kafkaObject.ReturnNotificationRequest;
import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.entities.Notify;
import com.library.notification.repositories.NotifyRepo;
import com.library.notification.services.ReturnNotifyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReturnNotifyServiceImpl implements ReturnNotifyService {
    NotifyRepo repo;

    @Override
    public NotifyResponse returnNotify(ReturnNotificationRequest request){
        return toNotifyResponse(repo.save(toNotify(request)));
    }

    @Override
    public Notify toNotify(ReturnNotificationRequest request){
        return Notify.builder()
                .body("You was return " + request.getBookName().toString())
                .date(request.getBorrowTime())
                .title("Return successful")
                .userId(request.getUserId())
                .send(false)
                .build();
    }

    @Override
    public NotifyResponse toNotifyResponse(Notify request){
        return NotifyResponse.builder()
                .body(request.getBody())
                .send(request.getSend())
                .date(request.getDate())
                .title(request.getTitle())
                .userId(request.getUserId())
                .build();
    }
}
