package com.library.auth_service.repositories;

import com.library.auth_service.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
    @Query("SELECT COUNT(r) > 0 FROM Role r WHERE r.roleName = :name")
    boolean existsByRoleName(@Param("name") String roleName);
}
