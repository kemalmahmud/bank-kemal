package com.bankkemal.account.controller;

import com.bankkemal.account.dto.request.AccountCreateRequestDto;
import com.bankkemal.account.dto.request.AccountEditRequestDto;
import com.bankkemal.account.dto.response.AccountDetailResponseDto;
import com.bankkemal.account.model.common.ApiResponse;
import com.bankkemal.account.model.common.BaseResponse;
import com.bankkemal.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private AccountService accountService;

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<AccountDetailResponseDto>> createAccount(
            @Valid @RequestBody AccountCreateRequestDto dto
    ) {
        AccountDetailResponseDto response = accountService.createAccount(dto);
        return BaseResponse.success("Account created successfully", response);
    }

    // GET ALL ACCOUNT BY USER ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<AccountDetailResponseDto>>> getAllUserAccounts(@PathVariable UUID userId) {
        List<AccountDetailResponseDto> list = accountService.getAccountsByUserId(userId);
        return BaseResponse.success("Account list retrieved", list);
    }

    // GET ALL ACCOUNT
    @GetMapping()
    public ResponseEntity<ApiResponse<List<AccountDetailResponseDto>>> getAllAccounts() {
        List<AccountDetailResponseDto> list = accountService.getAllActiveAccounts();
        return BaseResponse.success("Account list retrieved", list);
    }

    // GET ACCOUNT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDetailResponseDto>> getAccountById(@PathVariable UUID id) {
        AccountDetailResponseDto account = accountService.getAccountById(id);
        return BaseResponse.success("Account retrieved", account);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDetailResponseDto>> updateAccount(
            @PathVariable UUID id,
            @Valid @RequestBody AccountEditRequestDto dto
    ) {
        AccountDetailResponseDto updated = accountService.updateAccount(id, dto);
        return BaseResponse.success("Account updated successfully", updated);
    }

    // DELETE (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return BaseResponse.success("Account deleted successfully", null);
    }
}
