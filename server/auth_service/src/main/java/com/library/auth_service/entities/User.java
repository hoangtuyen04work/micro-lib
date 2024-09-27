package com.library.auth_service.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true, length = 64)
    String name;
    @Column(nullable = false, length = 64)
    String password;
    String email;
    String phone;
    @CreatedDate
    Date created_ad;
    @LastModifiedDate
    Date update_at;
    @ManyToMany
    List<Role> roles;
}
