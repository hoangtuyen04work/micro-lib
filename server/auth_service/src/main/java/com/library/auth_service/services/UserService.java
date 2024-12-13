package com.library.auth_service.services;

import com.library.auth_service.dtos.requests.UserCreationRequest;
import com.library.auth_service.dtos.responses.PageResponse;
import com.library.auth_service.dtos.responses.UserSimpleResponse;
import com.library.auth_service.entities.User;
import com.library.auth_service.exceptions.AppException;

public interface UserService {
    PageResponse<UserSimpleResponse> getBasicInfo(Integer page, Integer size);

    User emailVerify(String email, String password) throws AppException;

    User phoneVerify(String phone, String password) throws AppException;

    boolean isExistByEmail(String email);

    boolean isExistByPhone(String phone);

    User findByEmail(String email);

    User findByPhone(String phone);

    User findUserById(Long id);

    PageResponse<UserSimpleResponse> findUsersById(Integer userId, Integer page, Integer size);

    PageResponse<UserSimpleResponse> findUsersByName(String name, Integer page, Integer size);

    User createUser(UserCreationRequest request) throws AppException;
}
