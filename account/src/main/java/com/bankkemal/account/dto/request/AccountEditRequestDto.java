package com.bankkemal.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountEditRequestDto {
    @NotBlank(message = "User Id is required")
    private UUID userId;

    @NotBlank(message = "status is required")
    private String status; // hanya bisa ini yang di update
}

