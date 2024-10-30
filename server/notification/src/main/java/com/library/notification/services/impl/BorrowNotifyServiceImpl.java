package com.library.notification.services.impl;

import com.library.kafkaObject.BorrowNotificationRequest;
import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.entities.BorrowNotify;
import com.library.notification.repositories.BorrowNotifyRepo;
import com.library.notification.services.BorrowNotifyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BorrowNotifyServiceImpl  implements BorrowNotifyService {
    BorrowNotifyRepo repo;

    @Override
    public NotifyResponse borrowNotify(BorrowNotificationRequest request){

        return toNotify(repo.save(toBorrowNotify(request)));
    }
    @Override
    public BorrowNotify toBorrowNotify(BorrowNotificationRequest request){
        return BorrowNotify.builder()
                .body("You was borrow " + request.getBookName().stream())
                .date(request.getBorrowTime())
                .title("Borrow successful")
                .userId(request.getUserId())
                .build();
    }
    @Override
    public NotifyResponse toNotify(BorrowNotify request){
        return NotifyResponse.builder()
                .body(request.getBody())
                .date(request.getDate())
                .title(request.getTitle())
                .userId(request.getUserId())
                .build();
    }
}
