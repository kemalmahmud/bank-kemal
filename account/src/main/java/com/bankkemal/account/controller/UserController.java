package com.bankkemal.account.controller;

import com.bankkemal.account.dto.request.UserCreateRequestDto;
import com.bankkemal.account.dto.request.UserEditRequestDto;
import com.bankkemal.account.dto.response.UserDetailResponseDto;
import com.bankkemal.account.model.common.BaseResponse;
import com.bankkemal.account.model.common.ApiResponse;
import com.bankkemal.account.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDetailResponseDto>>> getAllUsers() {
        List<UserDetailResponseDto> users = userService.getUsers();
        return BaseResponse.success("Users retrieved successfully", users);
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserDetailResponseDto>> createUser(@RequestBody UserCreateRequestDto request) {
        UserDetailResponseDto result = userService.createUser(request);
        return BaseResponse.success("User created successfully", result);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDetailResponseDto>> getUserById(@PathVariable UUID id) {
        UserDetailResponseDto user = userService.getUserById(id);
        return BaseResponse.success("User retrieved successfully", user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDetailResponseDto>> updateUser(
            @PathVariable UUID id,
            @RequestBody UserEditRequestDto request
    ) {
        UserDetailResponseDto updated = userService.updateUser(id, request);
        return BaseResponse.success("User updated successfully", updated);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return BaseResponse.success("User deleted successfully", null);
    }
}
