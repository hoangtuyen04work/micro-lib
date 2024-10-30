package com.library.notification.services;

import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.dtos.responses.PageResponse;
import com.library.notification.entities.Notify;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotifyDataService {

    void readNotify(List<Long> ids, Long userId);

    PageResponse<NotifyResponse> getNotify(Long userId, Integer page, Integer size);

    PageResponse<NotifyResponse> toPageResponse(Page<Notify> request);

    List<NotifyResponse> toNotifyResponse(List<Notify> request);

    NotifyResponse toNotifyResponse(Notify request);
}
