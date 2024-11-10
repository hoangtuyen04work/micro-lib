package com.library.auth_service.utils.impl;

import com.library.auth_service.dtos.requests.RoleRequest;
import com.library.auth_service.dtos.requests.UserRequest;
import com.library.auth_service.dtos.responses.AuthResponse;
import com.library.auth_service.dtos.responses.RoleResponse;
import com.library.auth_service.dtos.responses.UserResponse;
import com.library.auth_service.entities.Role;
import com.library.auth_service.entities.Token;
import com.library.auth_service.entities.User;
import com.library.auth_service.utils.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MappingImpl implements Mapping {


    @Override
    public AuthResponse toAuthResponse(User user, Token token){
        return AuthResponse.builder()
                .id(user.getId())
                .token(token.getToken())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .refreshToken(token.getRefreshToken())
                .roles(toRoleResponses(user.getRoles()))
                .build();
    }

    @Override
    public UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .imageUrl(user.getImageUrl())
                .created_at(user.getCreated_ad())
                .updated_at(user.getUpdate_at())
                .roles(toRoleResponses(user.getRoles()))
                .build();
    }

    @Override
    public User toUser(UserRequest request){
        return User.builder()
                .id(request.getId())
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .password(request.getPassword())
                .created_ad(request.getCreated_at())
                .update_at(request.getUpdated_at())
                .roles(toRoles(request.getRoles()))
                .build();
    }

    @Override
    public Role toRole(RoleRequest request){
        return Role.builder()
                .roleName(request.getRoleName())
                .id(request.getId())
                .build();
    }

    @Override
    public List<Role> toRoles(List<RoleRequest> requests){
        return requests.stream().map(this::toRole).toList();
    }

    @Override
    public RoleResponse toRoleResponse(Role role){
        return RoleResponse.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    @Override
    public List<RoleResponse> toRoleResponses(List<Role> roles){
        return roles.stream().map(this::toRoleResponse).toList();
    }
}
