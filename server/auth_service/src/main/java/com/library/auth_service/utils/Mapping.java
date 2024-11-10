package com.library.auth_service.utils;


import com.library.auth_service.dtos.requests.RoleRequest;
import com.library.auth_service.dtos.requests.UserRequest;
import com.library.auth_service.dtos.responses.AuthResponse;
import com.library.auth_service.dtos.responses.RoleResponse;
import com.library.auth_service.dtos.responses.UserResponse;
import com.library.auth_service.entities.Role;
import com.library.auth_service.entities.Token;
import com.library.auth_service.entities.User;

import java.util.List;

public interface Mapping {
    AuthResponse toAuthResponse(User user, Token token);

    UserResponse toUserResponse(User user);

    User toUser(UserRequest request);

    Role toRole(RoleRequest request);

    List<Role> toRoles(List<RoleRequest> requests);

    RoleResponse toRoleResponse(Role role);

    List<RoleResponse> toRoleResponses(List<Role> roles);
}
