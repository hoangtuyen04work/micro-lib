package com.library.auth_service.utils;


import com.library.auth_service.dtos.requests.RoleRequest;
import com.library.auth_service.dtos.requests.UserCreationRequest;
import com.library.auth_service.dtos.requests.UserRequest;
import com.library.auth_service.dtos.responses.*;
import com.library.auth_service.entities.Role;
import com.library.auth_service.entities.Token;
import com.library.auth_service.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface Mapping {
    PageResponse<UserSimpleResponse> toPageUserSimpleResponse(Page<User> pages);

    UserSimpleResponse toUserSimpleResponse(User user);

    AuthResponse toAuthResponse(User user, Token token);

    UserResponse toUserResponse(User user);

    User toUser(UserRequest request);

    User toUser(UserCreationRequest request);

    Role toRole(RoleRequest request);

    List<Role> toRoles(List<RoleRequest> requests);

    RoleResponse toRoleResponse(Role role);

    List<RoleResponse> toRoleResponses(List<Role> roles);
}
