package com.library.borrow_service.mapping.impl;

import com.library.borrow_service.dtos.responses.BorrowResponse;
import com.library.borrow_service.dtos.responses.PageResponse;
import com.library.borrow_service.entities.Borrow;
import com.library.borrow_service.mapping.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class MappingImpl implements Mapping {


    @Override
    public PageResponse<BorrowResponse> toPageResponseFromBorrow(Page<Borrow> pages){
        return PageResponse.<BorrowResponse>builder()
                .content(pages.stream().map(this::toBorrowResponse).toList())
                .pageNumber(pages.getNumber())
                .pageSize(pages.getSize())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .build();
    }

    @Override
    public Borrow toBorrow(Long bookId, Long userId){
         return Borrow.builder()
                    .bookId(bookId)
                    .userId(userId)
                    .borrowDate(LocalDateTime.now())
                    .returnDate(LocalDateTime.now().plusWeeks(1))
                    .status("BORROWED")
                    .build();
    }

    @Override
    public Borrow toReturn(Long bookId, Long userId){
        return Borrow.builder()
                .bookId(bookId)
                .userId(userId)
                .borrowDate(LocalDateTime.now())
                .returnDate(LocalDateTime.now().plusWeeks(1))
                .status("RETURNED")
                .build();
    }


    @Override
    public PageResponse<Long> toPageResponse(Page<Long> pages){
        return PageResponse.<Long>builder()
                .content(pages.getContent())
                .pageNumber(pages.getNumber())
                .pageSize(pages.getSize())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .build();
    }

    @Override
    public BorrowResponse toBorrowResponse(Borrow borrow){
        return  BorrowResponse.builder()
                .id(borrow.getId())
                .bookId(borrow.getBookId())
                .borrowDate(borrow.getBorrowDate())
                .returnDate(borrow.getReturnDate())
                .status(borrow.getStatus())
                .userId(borrow.getUserId())
                .build();
    }

    @Override
    public Borrow toBorrow(Borrow borrow){
        return  Borrow.builder()
                .id(borrow.getId())
                .bookId(borrow.getBookId())
                .borrowDate(borrow.getBorrowDate())
                .returnDate(borrow.getReturnDate())
                .status(borrow.getStatus())
                .userId(borrow.getUserId())
                .build();
    }
}
