package com.library.auth_service.controllers;

import com.library.auth_service.dtos.ApiResponse;
import com.library.auth_service.dtos.requests.AuthRequest;
import com.library.auth_service.dtos.requests.TokenRequest;
import com.library.auth_service.dtos.requests.UserRequest;
import com.library.auth_service.dtos.responses.AuthResponse;
import com.library.auth_service.dtos.responses.BooleanResponse;
import com.library.auth_service.exceptions.AppException;
import com.library.auth_service.services.impl.TokenServiceImpl;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {
    TokenServiceImpl tokenServiceImpl;

    @PostMapping("/authenticate")
    public ApiResponse<BooleanResponse> authenticate(@RequestBody AuthRequest authRequest) throws AppException {
        return ApiResponse.<BooleanResponse>builder()
                .data(tokenServiceImpl.authenticate(authRequest))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody UserRequest request) throws AppException, JOSEException {
        return ApiResponse.<AuthResponse>builder()
                .data(tokenServiceImpl.login(request))
                .build();
    }
    @PostMapping("/signup")
    public ApiResponse<AuthResponse> signup(@ModelAttribute UserRequest request) throws AppException, JOSEException {
        return ApiResponse.<AuthResponse>builder()
                .data(tokenServiceImpl.signup(request))
                .build();
    }
    @PostMapping("/logoutt")
    public void logout(@RequestBody TokenRequest request){
        tokenServiceImpl.logout(request);
    }
}
