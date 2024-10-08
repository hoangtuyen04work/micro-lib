package com.library.auth_service.services.impl;

import com.library.auth_service.dtos.requests.RoleRequest;
import com.library.auth_service.dtos.requests.UserRequest;
import com.library.auth_service.dtos.responses.UserResponse;
import com.library.auth_service.entities.User;
import com.library.auth_service.exceptions.AppException;
import com.library.auth_service.exceptions.ErrorCode;
import com.library.auth_service.repositories.UserRepo;
import com.library.auth_service.repositories.httpclient.AmazonS3Client;
import com.library.auth_service.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepo userRepo;
    RoleServiceImpl roleServiceImpl;
    PasswordEncoder passwordEncoder;
    AmazonS3Client amazonS3Client;

    @Override
    public User emailVerify(String email, String password) throws AppException {
        if(!isExistByEmail(email)) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        User user = findByEmail(email);
        if(passwordEncoder.matches(password, user.getPassword())){
            return user;
        }
        else{
            throw  new AppException(ErrorCode.WRONG_PASSWORD_OR_USERNAME);
        }
    }

    @Override
    public User phoneVerify(String phone, String password) throws AppException {
        if(!isExistByPhone(phone)) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        User user = findByPhone(phone);
        if(passwordEncoder.matches(password, user.getPassword())){
            return user;
        }
        else{
            throw  new AppException(ErrorCode.WRONG_PASSWORD_OR_USERNAME);
        }
    }

    @Override
    public boolean isExistByEmail(String email){
        return userRepo.existsByEmail(email);
    }

    @Override
    public boolean isExistByPhone(String phone){
        return userRepo.existsByPhone(phone);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User findByPhone(String phone){
        return userRepo.findByPhone(phone);
    }

    @Override
    public User findUserById(Long id){
        return userRepo.findById(id).orElseThrow();
    }

    @Override
    public User createUser(UserRequest request) throws AppException {
        if(request.getName() == null || userRepo.existsByName(request.getName())) throw new AppException(ErrorCode.USERNAME_EXISTED);
        if(request.getEmail() == null && request.getPhone() == null){
            throw new AppException(ErrorCode.INVALID_INPUT);
        }
        else if(request.getEmail() != null && userRepo.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        else if(request.getPhone() != null && userRepo.existsByPhone(request.getPhone())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        request.setRoles(List.of(new RoleRequest("USER")));
        User user = toUser(request);
        if(request.getMultipartFile() != null) user.setImageUrl(amazonS3Client.uploadImage(request.getMultipartFile()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    @Override
    public UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .imageUrl(user.getImageUrl())
                .created_at(user.getCreated_ad())
                .updated_at(user.getUpdate_at())
                .roles(roleServiceImpl.toRoleResponses(user.getRoles()))
                .build();
    }

    @Override
    public User toUser(UserRequest request){
        return User.builder()
                .id(request.getId())
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .password(request.getPassword())
                .created_ad(request.getCreated_at())
                .update_at(request.getUpdated_at())
                .roles(roleServiceImpl.toRoles(request.getRoles()))
                .build();
    }



}
