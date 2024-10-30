package com.library.notification.services;

import com.library.kafkaObject.ReturnNotificationRequest;
import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.entities.Notify;

public interface ReturnNotifyService {
    NotifyResponse returnNotify(ReturnNotificationRequest request);

    Notify toNotify(ReturnNotificationRequest request);

    NotifyResponse toNotifyResponse(Notify request);
}
