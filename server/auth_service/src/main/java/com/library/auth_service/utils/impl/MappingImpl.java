package com.library.auth_service.utils.impl;

import com.library.auth_service.dtos.requests.RoleRequest;
import com.library.auth_service.dtos.requests.UserCreationRequest;
import com.library.auth_service.dtos.requests.UserRequest;
import com.library.auth_service.dtos.responses.*;
import com.library.auth_service.entities.Role;
import com.library.auth_service.entities.Token;
import com.library.auth_service.entities.User;
import com.library.auth_service.utils.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.library.auth_service.dtos.responses.UserSimpleResponse;

import java.util.List;

@Component
public class MappingImpl implements Mapping {


    @Override
    public PageResponse<UserSimpleResponse> toPageUserSimpleResponse(Page<User> pages){
        return PageResponse.<UserSimpleResponse>builder()
                .pageSize(pages.getSize())
                .content(pages.getContent().stream().map(this::toUserSimpleResponse).toList())
                .pageNumber(pages.getNumber())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .build();
    }

    @Override
    public UserSimpleResponse toUserSimpleResponse(User user){
        return UserSimpleResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .build();
    }

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
                .created_at(user.getCreatedAt())
                .updated_at(user.getUpdateAt())
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
                .createdAt(request.getCreated_at())
                .updateAt(request.getUpdated_at())
                .roles(toRoles(request.getRoles()) != null ? toRoles(request.getRoles()) : null)
                .build();
    }

    @Override
    public User toUser(UserCreationRequest request){
        List<Role> roles = new java.util.ArrayList<>();
        roles.add(Role.builder()
                        .id(2L)
                        .roleName("USER")
                        .build());
        return User.builder()
                .id(request.getId())
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .password(request.getPassword())
                .createdAt(request.getCreated_at())
                .updateAt(request.getUpdated_at())
                .roles(roles)
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
