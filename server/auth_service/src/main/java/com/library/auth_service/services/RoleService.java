package com.library.auth_service.services;

import com.library.auth_service.dtos.requests.RoleRequest;
import com.library.auth_service.dtos.responses.RoleResponse;
import com.library.auth_service.entities.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRole(String roleName);

    Role createRole(Role role);

    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    List<Role> toRoles(List<RoleRequest> request);

    List<RoleResponse> toRoleResponses(List<Role> request);
}
