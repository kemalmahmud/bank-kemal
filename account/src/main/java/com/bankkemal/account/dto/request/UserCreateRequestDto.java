package com.bankkemal.account.dto.request;

import lombok.Data;

@Data
public class UserCreateRequestDto {
    private String username;
    private String password;
    private String name;
    private String address;
}

