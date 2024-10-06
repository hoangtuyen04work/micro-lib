package com.library.notification.services;

import com.library.notification.dtos.requests.BorrowNotificationRequest;
import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.entities.BorrowNotify;

public interface BorrowNotifyService {
    NotifyResponse borrowNotify(BorrowNotificationRequest request);

    BorrowNotify toBorrowNotify(BorrowNotificationRequest request);

    NotifyResponse toNotify(BorrowNotify request);
}
