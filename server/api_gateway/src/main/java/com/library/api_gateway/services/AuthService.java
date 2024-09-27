package com.library.api_gateway.services;

import com.library.api_gateway.dtos.ApiResponse;
import com.library.api_gateway.dtos.AuthRequest;
import com.library.api_gateway.dtos.AuthResponse;
import com.library.api_gateway.repositories.AuthClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    AuthClient authClient;

    public Mono<ApiResponse<AuthResponse>>  authenticate(String token){
        return authClient.authenticate(AuthRequest.builder()
                .token(token)
                .build());
    }
}
