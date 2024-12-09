package com.library.borrow_service.mapping;

import com.library.borrow_service.dtos.responses.BorrowResponse;
import com.library.borrow_service.dtos.responses.PageResponse;
import com.library.borrow_service.entities.Borrow;
import org.springframework.data.domain.Page;

public interface Mapping {
    PageResponse<BorrowResponse> toPageResponseFromBorrow(Page<Borrow> pages);

    Borrow toBorrow(Long bookId, Long userId);

    Borrow toReturn(Long bookId, Long userId);

    PageResponse<Long> toPageResponse(Page<Long> pages);

    BorrowResponse toBorrowResponse(Borrow borrow);

    Borrow toBorrow(Borrow borrow);
}
