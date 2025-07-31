package com.bankkemal.account.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bankkemal.account.dto.request.UserCreateRequestDto;
import com.bankkemal.account.dto.request.UserEditRequestDto;
import com.bankkemal.account.dto.response.UserDetailResponseDto;
import com.bankkemal.account.exception.ResourceNotFoundException;
import com.bankkemal.account.model.User;
import com.bankkemal.account.model.UserDetail;
import com.bankkemal.account.repository.UserDetailRepository;
import com.bankkemal.account.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;


    public List<UserDetailResponseDto> getUsers() {
        return userRepository.findAllByIsDeletedFalse().stream()
                .map(user -> {
                    UserDetail detail = user.getUserDetail();
                    return UserDetailResponseDto.builder()
                            .userId(user.getUserId().toString())
                            .username(user.getUsername())
                            .name(detail.getName())
                            .address(detail.getAddress())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public UserDetailResponseDto getUserById(UUID id) {
        User user = userRepository.findByUserIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserDetail detail = user.getUserDetail();

        return UserDetailResponseDto.builder()
                .userId(user.getUserId().toString())
                .username(user.getUsername())
                .name(detail.getName())
                .address(detail.getAddress())
                .build();
    }

    public UserDetailResponseDto createUser(UserCreateRequestDto requestDto) {
        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(requestDto.getPassword());
        user = userRepository.save(user);

        UserDetail userDetail = new UserDetail();
        userDetail.setUser(user);
        userDetail.setName(requestDto.getName());
        userDetail.setAddress(requestDto.getAddress());
        userDetail = userDetailRepository.save(userDetail);

        return UserDetailResponseDto.builder()
                .userId(user.getUserId().toString())
                .username(user.getUsername())
                .name(userDetail.getName())
                .address(userDetail.getAddress())
                .build();
    }

    public UserDetailResponseDto updateUser(UUID id, UserEditRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserDetail detail = user.getUserDetail();
        detail.setName(requestDto.getName());
        detail.setAddress(requestDto.getAddress());
        userDetailRepository.save(detail);

        return UserDetailResponseDto.builder()
                .userId(detail.getUser().getUserId().toString())
                .username(detail.getUser().getUsername())
                .name(detail.getName())
                .address(detail.getAddress())
                .build();
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setDeleted(true);
        userRepository.save(user);
    }
}
