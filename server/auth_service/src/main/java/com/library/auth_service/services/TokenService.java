package com.library.auth_service.services;

import com.library.auth_service.dtos.requests.*;
import com.library.auth_service.dtos.responses.AuthResponse;
import com.library.auth_service.dtos.responses.BooleanResponse;
import com.library.auth_service.dtos.responses.TokenResponse;
import com.library.auth_service.entities.Role;
import com.library.auth_service.entities.Token;
import com.library.auth_service.entities.User;
import com.library.auth_service.exceptions.AppException;
import com.nimbusds.jose.JOSEException;

import java.util.List;
import java.util.Set;

public interface TokenService {

    void logout(AuthRequest request);

    AuthResponse login(UserRequest request) throws AppException, JOSEException;

    AuthResponse signup(UserCreationRequest request) throws AppException, JOSEException;

    BooleanResponse authenticate(AuthRequest authRequest) throws AppException;

    AuthResponse refreshTokenOk(RefreshTokenRequest request) throws AppException, JOSEException;

    Token refreshToken(String refreshToken) throws AppException, JOSEException;

    boolean verifyToken(String token) throws AppException;

    Token createToken(User user) throws JOSEException;

    Token createToken(User user, String refreshToken) throws JOSEException;

    String generateRefreshToken();

    String generateToken(User user) throws JOSEException;

    String buildRole(List<Role> roles);

    TokenResponse tokenResponse(Token token);

    Token toToken(TokenRequest request);
}
