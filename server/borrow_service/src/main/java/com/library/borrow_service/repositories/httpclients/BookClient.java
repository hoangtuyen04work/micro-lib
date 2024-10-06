package com.library.borrow_service.repositories.httpclients;

import com.library.borrow_service.configurations.AuthenticationRequestInterceptor;
import com.library.borrow_service.dtos.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${app.service.book.name}", url = "${app.service.book.url}",
        configuration =  {AuthenticationRequestInterceptor.class})
public interface BookClient {

    @PostMapping("/book/borrow")
    ApiResponse<Boolean> borrow(@RequestBody List<Long> id);

    @PutMapping("/book/return")
    ApiResponse<Boolean> returnBook(@RequestBody List<Long> bookIds);
}
