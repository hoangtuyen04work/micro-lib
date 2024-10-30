package com.library.notification.services.impl;

import com.library.kafkaObject.ReturnNotificationRequest;
import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.entities.ReturnNotify;
import com.library.notification.repositories.ReturnNotifyRepo;
import com.library.notification.services.ReturnNotifyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReturnNotifyServiceImpl implements ReturnNotifyService {
    ReturnNotifyRepo repo;

    @Override
    public NotifyResponse returnNotify(ReturnNotificationRequest request){
        return toNotify(repo.save(toReturnNotify(request)));
    }
    @Override
    public ReturnNotify toReturnNotify(ReturnNotificationRequest request){
        return ReturnNotify.builder()
                .body("You was return " + request.getBookName().stream())
                .date(request.getBorrowTime())
                .title("Return successful")
                .userId(request.getUserId())
                .build();
    }
    @Override
    public NotifyResponse toNotify(ReturnNotify request){
        return NotifyResponse.builder()
                .body(request.getBody())
                .date(request.getDate())
                .title(request.getTitle())
                .userId(request.getUserId())
                .build();
    }
}
