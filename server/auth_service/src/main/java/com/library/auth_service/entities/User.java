package com.library.auth_service.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String name;
    @Column(nullable = false)
    String password;
    String email;
    Long numberBorrowed;
    Long numberReturned;
    String phone;
    @CreatedDate
    Date createdAt;
    @LastModifiedDate
    Date updateAt;
    @ManyToMany
    @JoinTable(
            name = "user_roles", // Join table name
            joinColumns = @JoinColumn(name = "user_id"), // User's foreign key
            inverseJoinColumns = @JoinColumn(name = "role_id") // Role's foreign key
    )
    List<Role> roles;

    String imageUrl;
}
