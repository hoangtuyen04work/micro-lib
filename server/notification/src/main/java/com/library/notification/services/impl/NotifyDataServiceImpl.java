package com.library.notification.services.impl;

import com.library.notification.dtos.responses.NotifyResponse;
import com.library.notification.dtos.responses.PageResponse;
import com.library.notification.entities.Notify;
import com.library.notification.repositories.NotifyRepo;
import com.library.notification.services.NotifyDataService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotifyDataServiceImpl implements NotifyDataService {
    NotifyRepo repo;

    @Override
    public void readNotify(List<Long> ids, Long userId){
        for(Long id : ids){
            Optional<Notify> optional =repo.findById(id);
            Notify notify;
            if(optional.isEmpty()){
                continue;
            }
            else{
                notify = optional.get();
                notify.setSend(true);
                repo.save(notify);
            }
        }
    }

    @Override
    public PageResponse<NotifyResponse> getNotify(Long userId, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Notify> pages = repo.findByUserId(userId, pageable);
        return toPageResponse(pages);
    }

    @Override
    public PageResponse<NotifyResponse> toPageResponse(Page<Notify> request){
        return PageResponse.<NotifyResponse>builder()
                .pageSize(request.getSize())
                .totalPages(request.getTotalPages())
                .pageNumber(request.getNumber())
                .content(toNotifyResponse(request.getContent()))
                .totalElements(request.getTotalElements())
                .build();
    }

    @Override
    public List<NotifyResponse> toNotifyResponse(List<Notify> request){
        return request.stream().map(this::toNotifyResponse).toList();
    }

    @Override
    public NotifyResponse toNotifyResponse(Notify request){
        return NotifyResponse.builder()
                .body(request.getBody())
                .date(request.getDate())
                .id(request.getId())
                .send(request.getSend())
                .title(request.getTitle())
                .build();
    }
}
