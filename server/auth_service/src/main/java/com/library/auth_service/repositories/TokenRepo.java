package com.library.auth_service.repositories;

import com.library.auth_service.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, String> {
    boolean existsByRefreshToken(String refreshToken);
    boolean existsByToken(String token);
    Token findTokenByRefreshToken(String refreshToken);
    void deleteByToken(String token);
}
