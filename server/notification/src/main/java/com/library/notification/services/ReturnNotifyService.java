package com.library.notification.services;

import com.library.notification.dtos.requests.ReturnNotificationRequest;
import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.entities.ReturnNotify;

public interface ReturnNotifyService {
    NotifyResponse returnNotify(ReturnNotificationRequest request);

    ReturnNotify toReturnNotify(ReturnNotificationRequest request);

    NotifyResponse toNotify(ReturnNotify request);
}
