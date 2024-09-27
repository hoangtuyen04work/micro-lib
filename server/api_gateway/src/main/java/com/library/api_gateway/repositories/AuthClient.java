package com.library.api_gateway.repositories;

import com.library.api_gateway.dtos.ApiResponse;
import com.library.api_gateway.dtos.AuthRequest;
import com.library.api_gateway.dtos.AuthResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface AuthClient {
    @PostExchange(url="/authenticate", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<AuthResponse>> authenticate(@RequestBody AuthRequest authRequest);
}

