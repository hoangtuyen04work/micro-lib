package com.library.search_service.services;

import com.library.search_service.dtos.responses.BookResponse;
import com.library.search_service.dtos.responses.PageResponse;

public interface SearchService {
    PageResponse<BookResponse> search(String name, Long size, Long page);
}
