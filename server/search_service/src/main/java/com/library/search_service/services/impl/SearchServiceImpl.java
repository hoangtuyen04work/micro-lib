package com.library.search_service.services.impl;

import com.library.search_service.dtos.responses.BookResponse;
import com.library.search_service.dtos.responses.PageResponse;
import com.library.search_service.repositories.BookClient;
import com.library.search_service.services.SearchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchServiceImpl implements SearchService {
    BookClient bookClient;

    @Override
    public PageResponse<BookResponse> search(String name, Long size, Long page) {
        return bookClient.findBook(name, size, page).getData();
    }
}
