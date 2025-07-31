package com.bankkemal.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreateRequestDto {
    @NotBlank(message = "User Id is required")
    private UUID userId;

    @NotBlank(message = "Account type is required")
    private String accountType;

    private BigDecimal balance;

    @NotBlank(message = "currency type is required")
    private String currency;
}

