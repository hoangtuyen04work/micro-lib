package com.library.book_service.repositories.httpclient;
import com.library.book_service.configurations.AuthenticationRequestInterceptor;
import com.library.book_service.dtos.ApiResponse;
import com.library.book_service.dtos.responses.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "${app.service.borrow.name}", url = "${app.service.borrow.url}",
        configuration =  {AuthenticationRequestInterceptor.class})
public interface BorrowClient {
    @GetMapping("/top")
    ApiResponse<PageResponse<Long>> getTopBooks(@RequestParam Integer page, @RequestParam Integer size);
}