package com.library.borrow_service.repositories.httpclients;

import com.library.borrow_service.configurations.AuthenticationRequestInterceptor;
import com.library.borrow_service.dtos.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${app.service.book.name}", url = "${app.service.book.url}",
        configuration =  {AuthenticationRequestInterceptor.class})
public interface BookClient {

    @PostMapping("/book/borrows/{userId}")
    ApiResponse<Boolean> borrow(@RequestBody List<Long> id, @PathVariable Long userId);

    @PostMapping("/book/borrow/{userId}")
    ApiResponse<Boolean> borrow(@RequestBody Long id, @PathVariable Long userId);

    @PutMapping("/book/returns/{userId}")
    ApiResponse<Boolean> returnBook(@RequestBody List<Long> bookIds, @PathVariable Long userId);

    @PutMapping("/book/return/{userId}")
    ApiResponse<Boolean> returnBook(@RequestBody Long id, @PathVariable Long userId);
}
