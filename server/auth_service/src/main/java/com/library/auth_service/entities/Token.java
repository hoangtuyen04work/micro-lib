package com.library.auth_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor // Required for JPA
@AllArgsConstructor // This generates a constructor with all fields
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    @Column(length = 512)
    String token;
    LocalDateTime expiry_Token;
    String refreshToken;
    LocalDateTime expiry_refreshToken;
    Long userid;
}
