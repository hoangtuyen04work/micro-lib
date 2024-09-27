package com.library.auth_service.dtos.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {
    Long id;
    String name;
    String email;
    String phone;
    Date created_at;
    Date updated_at;
    String password;
    List<RoleRequest> roles;
}
