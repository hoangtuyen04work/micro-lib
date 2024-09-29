package com.library.auth_service.configurations;

import com.library.auth_service.entities.Role;
import com.library.auth_service.entities.User;
import com.library.auth_service.exceptions.AppException;
import com.library.auth_service.repositories.RoleRepo;
import com.library.auth_service.repositories.UserRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepo userRepo, RoleRepo roleRepo){
        return args->{
            List<Role> roles = new ArrayList<>();
            if(!roleRepo.existsByRoleName("ADMIN")){
                Role admin = Role.builder()
                        .roleName("ADMIN")
                        .build();
                roleRepo.save(admin);
                roles.add(admin);
            }
            if(!roleRepo.existsByRoleName("USER")){
                Role user = Role.builder()
                        .roleName("USER")
                        .build();
                roleRepo.save(user);
                roles.add(user);
            }
            if(!userRepo.existsByEmail("ADMIN")){
                User user = User.builder()
                        .roles(roles)
                        .name("ADMIN")
                        .email("ADMIN")
                        .password(passwordEncoder.encode("123"))
                        .phone("ADMIN")
                        .build();
                userRepo.save(user);
            }
        };
    }
}
