package com.bankkemal.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountCreateRequestDto {
    @NotBlank(message = "User Id is required")
    private UUID userId;

    @NotBlank(message = "Account type is required")
    private String accountType;

    private BigDecimal balance;

    @NotBlank(message = "currency type is required")
    private String currency;
}

