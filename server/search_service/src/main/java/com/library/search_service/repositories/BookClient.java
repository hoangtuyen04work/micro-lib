package com.library.search_service.repositories;

import com.library.search_service.configurations.AuthenticationRequestInterceptor;
import com.library.search_service.dtos.ApiResponse;
import com.library.search_service.dtos.responses.BookResponse;
import com.library.search_service.dtos.responses.PageResponse;
import lombok.experimental.FieldDefaults;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${app.service.book.name}", url = "${app.service.book.url}",
        configuration =  {AuthenticationRequestInterceptor.class})
public interface BookClient {
    @PostMapping("/book/numbers")
    ApiResponse<PageResponse<BookResponse>> findBook(@RequestParam String name,
                                                     @RequestParam Long size,
                                                     @RequestParam Long page);
}
