package com.bankkemal.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailResponseDto {
    private String accountId;
    private String userId;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String status;
}

