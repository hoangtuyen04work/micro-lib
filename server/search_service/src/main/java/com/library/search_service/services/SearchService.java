package com.library.search_service.services;

import com.library.search_service.dtos.responses.BookResponse;
import com.library.search_service.dtos.responses.BookResponseSimple;
import com.library.search_service.dtos.responses.PageResponse;

public interface SearchService {
    PageResponse<BookResponseSimple> search(String name, Long size, Long page);
}
