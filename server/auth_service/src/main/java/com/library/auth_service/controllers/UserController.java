package com.library.auth_service.controllers;

import com.library.auth_service.dtos.ApiResponse;
import com.library.auth_service.dtos.responses.PageResponse;
import com.library.auth_service.dtos.responses.UserSimpleResponse;
import com.library.auth_service.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/basic")
    public ApiResponse<PageResponse<UserSimpleResponse>>
                        fetchUserInfoBasic(@RequestParam(required = false, defaultValue = "0")Integer page,
                                           @RequestParam(required = false, defaultValue = "10")Integer size){
        return ApiResponse.<PageResponse<UserSimpleResponse>>builder()
                .data(userService.getBasicInfo(page, size))
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/find/user/id")
    public ApiResponse<PageResponse<UserSimpleResponse>>
                        findUserInfoBasicByUserId(@RequestParam(required = true, defaultValue = "0")Integer userId,
                                           @RequestParam(required = false, defaultValue = "0")Integer page,
                                           @RequestParam(required = false, defaultValue = "10")Integer size){
        return ApiResponse.<PageResponse<UserSimpleResponse>>builder()
                .data(userService.findUsersById(userId, page, size))
                .build();
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/find/user/name")
    public ApiResponse<PageResponse<UserSimpleResponse>>
                    findUserInfoBasicByName(@RequestParam(required = true, defaultValue = "0")String name,
                                            @RequestParam(required = false, defaultValue = "0")Integer page,
                                            @RequestParam(required = false, defaultValue = "10")Integer size){
        return ApiResponse.<PageResponse<UserSimpleResponse>>builder()
                .data(userService.findUsersByName(name, page, size))
                .build();
    }
}
