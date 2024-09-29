package com.library.auth_service.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor // Required for JPA
@AllArgsConstructor // This generates a constructor with all fields
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String roleName;
    @ManyToMany(mappedBy = "roles")
    List<User> users;
}
