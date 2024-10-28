package com.library.search_service.repositories;

import com.library.search_service.configurations.AuthenticationRequestInterceptor;
import com.library.search_service.dtos.ApiResponse;
import com.library.search_service.dtos.responses.BookResponse;
import com.library.search_service.dtos.responses.BookResponseSimple;
import com.library.search_service.dtos.responses.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${app.service.book.name}", url = "${app.service.book.url}",
        configuration =  {AuthenticationRequestInterceptor.class})
public interface BookClient {
    @GetMapping("/book/search")
    ApiResponse<PageResponse<BookResponseSimple>> findBook(@RequestParam String name,
                                                           @RequestParam Long size,
                                                           @RequestParam Long page);
}
