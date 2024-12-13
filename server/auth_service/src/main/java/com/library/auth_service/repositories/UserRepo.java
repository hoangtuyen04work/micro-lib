package com.library.auth_service.repositories;

import com.library.auth_service.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByName(String name);
    User findByEmail(String email);
    User findByPhone(String phone);
    Page<User> findByName(String name, PageRequest pageRequest);
    Page<User> findById(Integer userId, PageRequest pageRequest);
}
