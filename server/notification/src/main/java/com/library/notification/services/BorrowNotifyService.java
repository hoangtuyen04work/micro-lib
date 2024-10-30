package com.library.notification.services;

import com.library.kafkaObject.BorrowNotificationRequest;
import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.entities.Notify;

public interface BorrowNotifyService {
    NotifyResponse borrowNotify(BorrowNotificationRequest request);

    Notify toNotify(BorrowNotificationRequest request);

    NotifyResponse toNotifyResponse(Notify request);
}
