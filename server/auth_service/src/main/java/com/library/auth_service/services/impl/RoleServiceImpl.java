package com.library.auth_service.services.impl;

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
        return roleRepo.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role findRole(String name){
        return roleRepo.findByRoleName(name);
    }

    @Override
    public List<Role> findRoles(List<String> request){
        List<Role> roles = new ArrayList<>();
        for(String r : request){
            roles.add(findRole(r));
        }
        return roles;
    }
}
