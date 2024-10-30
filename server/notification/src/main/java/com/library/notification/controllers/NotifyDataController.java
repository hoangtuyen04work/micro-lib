package com.library.notification.controllers;

import com.library.notification.dtos.ApiResponse;
import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.dtos.responses.PageResponse;
import com.library.notification.services.NotifyDataService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController()
@RequiredArgsConstructor
public class NotifyDataController {
    NotifyDataService service;

    @GetMapping("/notify")
    public ApiResponse<PageResponse<NotifyResponse>> getNotify(@RequestParam Long userId,
                                                               @RequestParam(defaultValue = "10") Integer size,
                                                               @RequestParam(defaultValue = "0") Integer page){
        return ApiResponse.<PageResponse<NotifyResponse>>builder()
                .data(service.getNotify(userId, size, page))
                .build();
    }

    @PutMapping("/{userId}")
    public void readNotify(@RequestBody List<Long> ids, @PathVariable Long userId){
        service.readNotify(ids, userId);
    }
}
