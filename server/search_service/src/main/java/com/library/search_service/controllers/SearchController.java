package com.library.search_service.controllers;

import com.library.search_service.dtos.ApiResponse;
import com.library.search_service.dtos.responses.BookResponse;
import com.library.search_service.dtos.responses.PageResponse;
import com.library.search_service.services.SearchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SearchController {
    SearchService searchService;

    @GetMapping()
    public ApiResponse<PageResponse<BookResponse>> search(@RequestParam String name,
                                                          @RequestParam(defaultValue = "10")Long size,
                                                          @RequestParam(defaultValue = "1")Long page) {
        return ApiResponse.<PageResponse<BookResponse>>builder()
                .data(searchService.search(name, size, page))
                .build();
    }
}
