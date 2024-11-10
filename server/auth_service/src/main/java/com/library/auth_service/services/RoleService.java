package com.library.auth_service.services;

import com.library.auth_service.entities.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRole(String roleName);

    Role createRole(Role role);

    Role findRole(String name);

    List<Role> findRoles(List<String> request);
}
