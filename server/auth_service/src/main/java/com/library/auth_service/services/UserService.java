package com.library.auth_service.services;

import com.library.auth_service.dtos.requests.UserRequest;
import com.library.auth_service.dtos.responses.UserResponse;
import com.library.auth_service.entities.User;
import com.library.auth_service.exceptions.AppException;

public interface UserService {
    User emailVerify(String email, String password) throws AppException;

    User phoneVerify(String phone, String password) throws AppException;

    boolean isExistByEmail(String email);

    boolean isExistByPhone(String phone);

    User findByEmail(String email);

    User findByPhone(String phone);

    User findUserById(Long id);

    User createUser(UserRequest request) throws AppException;
}
