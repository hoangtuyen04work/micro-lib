package com.library.notification.services.impl;

import com.library.kafkaObject.BorrowNotificationRequest;
import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.entities.Notify;
import com.library.notification.repositories.NotifyRepo;
import com.library.notification.services.BorrowNotifyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BorrowNotifyServiceImpl implements BorrowNotifyService {
    NotifyRepo repo;

    @Override
    public NotifyResponse borrowNotify(BorrowNotificationRequest request){
        return toNotifyResponse(repo.save(toNotify(request)));
    }

    @Override
    public Notify toNotify(BorrowNotificationRequest request){
        return Notify.builder()
                .body("You was borrow " + request.getBookName().toString())
                .date(request.getBorrowTime())
                .title("Borrow successful")
                .userId(request.getUserId())
                .send(false)
                .build();
    }

    @Override
    public NotifyResponse toNotifyResponse(Notify request){
        return NotifyResponse.builder()
                .body(request.getBody())
                .date(request.getDate())
                .title(request.getTitle())
                .userId(request.getUserId())
                .send(request.getSend())
                .build();
    }
}
