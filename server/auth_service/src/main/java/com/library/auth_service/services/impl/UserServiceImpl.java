package com.library.auth_service.services.impl;

import com.library.auth_service.dtos.requests.UserCreationRequest;
import com.library.auth_service.dtos.responses.PageResponse;
import com.library.auth_service.dtos.responses.UserSimpleResponse;
import com.library.auth_service.entities.User;
import com.library.auth_service.exceptions.AppException;
import com.library.auth_service.exceptions.ErrorCode;
import com.library.auth_service.repositories.UserRepo;
import com.library.auth_service.repositories.httpclient.AmazonS3Client;
import com.library.auth_service.services.UserService;
import com.library.auth_service.utils.Mapping;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepo userRepo;
    PasswordEncoder passwordEncoder;
    Mapping mapping;
    AmazonS3Client amazonS3Client;

    @Override
    public PageResponse<UserSimpleResponse> getBasicInfo(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> pages = userRepo.findAll(pageable);
        return mapping.toPageUserSimpleResponse(pages);
    }

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
    public User createUser(UserCreationRequest request) throws AppException {
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
        User user = mapping.toUser(request);
        if(request.getMultipartFile() != null) user.setImageUrl(amazonS3Client.uploadImage(request.getMultipartFile()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
