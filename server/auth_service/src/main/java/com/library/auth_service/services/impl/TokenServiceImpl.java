package com.library.auth_service.services.impl;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import com.library.auth_service.dtos.requests.*;
import com.library.auth_service.dtos.responses.AuthResponse;
import com.library.auth_service.dtos.responses.BooleanResponse;
import com.library.auth_service.dtos.responses.TokenResponse;
import com.library.auth_service.entities.Role;
import com.library.auth_service.entities.Token;
import com.library.auth_service.entities.User;
import com.library.auth_service.exceptions.AppException;
import com.library.auth_service.exceptions.ErrorCode;
import com.library.auth_service.repositories.TokenRepo;
import com.library.auth_service.services.TokenService;
import com.library.auth_service.utils.Mapping;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenServiceImpl implements TokenService {
    UserServiceImpl userServiceImpl;
    TokenRepo tokenRepo;
    @Value("${jwt.signerKey}")
    @NonFinal
    String signerKey;
    Mapping mapping;

    @Override
    @Transactional
    public void logout(AuthRequest request){
        tokenRepo.deleteByToken(request.getToken());
    }

    @Override
    public AuthResponse login(UserRequest request) throws AppException, JOSEException {
        User user = new User();
        if(request.getEmail() != null ){
            user = userServiceImpl.emailVerify(request.getEmail(), request.getPassword());
        }
        else if(request.getPhone() != null){
            user = userServiceImpl.phoneVerify(request.getPhone(), request.getPassword());
        }
        Token token = createToken(user);
        tokenRepo.save(token);
        return mapping.toAuthResponse(user, token);
    }

    @Override
    public AuthResponse signup(UserCreationRequest request) throws AppException, JOSEException {
        User user = userServiceImpl.createUser(request);
        Token token = createToken(user);
        tokenRepo.save(token);
        return mapping.toAuthResponse(user, token);
    }

    @Override
    public BooleanResponse authenticate(AuthRequest authRequest) throws AppException {
        if(!tokenRepo.existsByRefreshToken(authRequest.getRefreshToken()) && !tokenRepo.existsByToken(authRequest.getToken())){
            throw  new AppException(ErrorCode.TOKEN_INVALID);
        }
        return BooleanResponse.builder()
                .isValid(verifyToken(authRequest.getToken()))
                .build();
    }

    @Override
    public AuthResponse refreshTokenOk(RefreshTokenRequest request) throws AppException {
        if(!tokenRepo.existsByRefreshToken(request.getRefreshToken())) throw  new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
        Token token = tokenRepo.findTokenByRefreshToken(request.getRefreshToken());
        if(token.getExpiry_refreshToken().isBefore(LocalDateTime.now())) throw  new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
        tokenRepo.delete(token);
        User user = userServiceImpl.findUserById(token.getUserid());
        tokenRepo.save(token);
        return mapping.toAuthResponse(user, token);
    }

    @Override
    public Token refreshToken(String refreshToken) throws AppException, JOSEException {
        if(tokenRepo.existsByRefreshToken(refreshToken)) throw  new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
        Token token = tokenRepo.findTokenByRefreshToken(refreshToken);
        if(token.getExpiry_refreshToken().isBefore(LocalDateTime.now())) throw  new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
        tokenRepo.delete(token);
        return createToken(userServiceImpl.findUserById(token.getUserid()));
    }

    @Override
    public boolean verifyToken(String token) throws AppException {
        try{
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean verified = signedJWT.verify(verifier);
            if(!verified || expiryTime.before(new Date())){
                throw  new AppException(ErrorCode.INVALID_INPUT);
            }
            return true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_INPUT);
        }
    }

    @Override
    public Token createToken(User user) throws JOSEException {
        LocalDateTime expiryToken = LocalDateTime.ofInstant(
                Instant.now().plus(15 * 24 * 60 * 60, ChronoUnit.SECONDS),
                ZoneId.systemDefault() // Sử dụng múi giờ hệ thống
        );

        LocalDateTime expiryRefreshToken = LocalDateTime.ofInstant(
                Instant.now().plus(30 * 24 * 60 * 60, ChronoUnit.SECONDS),
                ZoneId.systemDefault() // Sử dụng múi giờ hệ thống
        );
        return Token.builder()
                .userid(user.getId())
                .token(generateToken(user))
                .refreshToken(generateRefreshToken())
                .expiry_Token(expiryToken)
                .expiry_refreshToken(expiryRefreshToken)
                .build();
    }

    @Override
    public String generateRefreshToken(){
        return UUID.randomUUID().toString();
    }

    @Override
    public String generateToken(User user) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        String roleScope = buildRole(user.getRoles());
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(String.valueOf(user.getId()))
                .issuer("LIBRARY")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(15*24*60*60, ChronoUnit.SECONDS)))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", roleScope)
                .build();
        JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(jwtClaimsSet.toJSONObject()));
        jwsObject.sign(new MACSigner(signerKey.getBytes()));
        return jwsObject.serialize();
    }

    @Override
    public  String buildRole(List<Role> roles){
        StringJoiner joiner = new StringJoiner(" ");
        for(Role role : roles){
            joiner.add(role.getRoleName());
        }
        return joiner.toString();
    }

    @Override
    public TokenResponse tokenResponse(Token token){
        return TokenResponse.builder()
                .refreshToken(token.getRefreshToken())
                .token(token.getToken())
                .build();
    }

    @Override
    public Token toToken(TokenRequest request){
        return Token.builder()
                .refreshToken(request.getRefreshToken())
                .token(request.getToken())
                .build();
    }
}
