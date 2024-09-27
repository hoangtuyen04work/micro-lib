package com.library.auth_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Token {
    @Id
    String token;
    Date expiry_Token;
    String refreshToken;
    Date expiry_refreshToken;
    Long userid;
}
