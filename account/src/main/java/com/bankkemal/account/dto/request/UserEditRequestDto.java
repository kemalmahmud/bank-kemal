package com.bankkemal.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserEditRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    private String address;
}

