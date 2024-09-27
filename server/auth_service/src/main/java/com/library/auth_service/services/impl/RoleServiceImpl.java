package com.library.auth_service.services.impl;

import com.library.auth_service.dtos.requests.RoleRequest;
import com.library.auth_service.dtos.responses.RoleResponse;
import com.library.auth_service.entities.Role;
import com.library.auth_service.repositories.RoleRepo;
import com.library.auth_service.services.RoleService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepo roleRepo;
    @Override
    public Role getRole(String roleName) {
        return roleRepo.findByRoleName(roleName);
    }

    @Override
    public Role createRole(Role role) {
        return null;
    }
    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
    @Override
    public Role toRole(RoleRequest request){
        return Role.builder()
                .roleName(request.getRoleName())
                .build();
    }
    @Override
    public RoleResponse toRoleResponse(Role role){
        return RoleResponse.builder()
                .roleName(role.getRoleName())
                .build();
    }
    @Override
    public List<Role> toRoles(List<RoleRequest> request){
        List<Role> roles = new ArrayList<>();
        for(RoleRequest r : request){
            roles.add(toRole(r));
        }
        return roles;
    }
    @Override
    public List<RoleResponse> toRoleResponses(List<Role> request){
        List<RoleResponse> roleResponses = new ArrayList<>();
        for(Role r : request){
            roleResponses.add(toRoleResponse(r));
        }
        return roleResponses;
    }
}
