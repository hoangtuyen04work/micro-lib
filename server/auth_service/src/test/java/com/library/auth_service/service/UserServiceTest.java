package com.library.auth_service.service;


import com.library.auth_service.dtos.requests.UserCreationRequest;
import com.library.auth_service.entities.Role;
import com.library.auth_service.entities.User;
import com.library.auth_service.exceptions.AppException;
import com.library.auth_service.repositories.UserRepo;
import com.library.auth_service.repositories.httpclient.AmazonS3Client;
import com.library.auth_service.services.UserService;
import com.library.auth_service.utils.Mapping;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;
    @MockBean
    private AmazonS3Client amazonS3Client;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private Mapping mapping;

    private UserCreationRequest userCreationRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userCreationRequest = UserCreationRequest.builder()
                .email("zzz")
                .name("zzz")
                .password("zzz")
                .phone("000")
                .created_at(new Date())
                .updated_at(new Date())
                .build();

        Role role = Role.builder()
                .roleName("ADMIN")
                .id(1L)
                .build();

        user = User.builder()
                .id(111L)
                .email("zzz")
                .name("zzz")
                .password("hashedPassword") // Mocked hashed password
                .phone("000")
                .roles(List.of(role))
                .build();
    }

    @Test
    void createUser_success() throws AppException {
        // Mocking behavior
        Mockito.when(userRepo.existsByName(userCreationRequest.getName())).thenReturn(false);
        Mockito.when(userRepo.existsByEmail(userCreationRequest.getEmail())).thenReturn(false);
        Mockito.when(userRepo.existsByPhone(userCreationRequest.getPhone())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(userCreationRequest.getPassword())).thenReturn("hashedPassword");
        Mockito.when(mapping.toUser(userCreationRequest)).thenReturn(user);
        Mockito.when(amazonS3Client.uploadImage(userCreationRequest.getMultipartFile())).thenReturn("image_url");
        Mockito.when(userRepo.save(Mockito.any(User.class))).thenReturn(user);

        // Execute
        User createdUser = userService.createUser(userCreationRequest);

        // Assert
        Assertions.assertThat(createdUser.getId()).isEqualTo(111L);
        Assertions.assertThat(createdUser.getEmail()).isEqualTo(userCreationRequest.getEmail());
        Assertions.assertThat(createdUser.getPhone()).isEqualTo(userCreationRequest.getPhone());
        Assertions.assertThat(createdUser.getImageUrl()).isEqualTo("image_url");
        Assertions.assertThat(createdUser.getPassword()).isEqualTo("hashedPassword");
    }
}

