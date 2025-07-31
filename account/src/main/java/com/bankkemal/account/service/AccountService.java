package com.bankkemal.account.service;

import com.bankkemal.account.dto.request.AccountCreateRequestDto;
import com.bankkemal.account.dto.request.AccountEditRequestDto;
import com.bankkemal.account.dto.response.AccountDetailResponseDto;
import com.bankkemal.account.exception.ResourceNotFoundException;
import com.bankkemal.account.model.Account;
import com.bankkemal.account.model.User;
import com.bankkemal.account.repository.AccountRepository;
import com.bankkemal.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public AccountDetailResponseDto createAccount(AccountCreateRequestDto request) {
        // Validasi minimal saldo
        if (request.getBalance() == null || request.getBalance().compareTo(BigDecimal.valueOf(50000)) < 0) {
            throw new IllegalArgumentException("Minimum balance is 50000");
        }

        // Validasi user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Generate 8 digit angka acak untuk nomor akun
        String accountNumber = generateAccountNumber();

        Account account = new Account();
        account.setUser(user);
        account.setAccountNumber(accountNumber);
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getBalance());
        account.setCurrency(request.getCurrency());
        account.setStatus("Open"); // default status
        account.setDeleted(false);

        account = accountRepository.save(account);

        return mapToDto(account);
    }

    public List<AccountDetailResponseDto> getAccountsByUserId(UUID userId) {
        return accountRepository.findActiveAccountsByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public AccountDetailResponseDto getAccountById(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return mapToDto(account);
    }

    public List<AccountDetailResponseDto> getAllActiveAccounts() {
        return accountRepository.findAllActiveAccounts().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public AccountDetailResponseDto updateAccount(UUID accountId, AccountEditRequestDto request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // Validasi user exist
        userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        account.setStatus(request.getStatus());

        return mapToDto(accountRepository.save(account));
    }

    public void deleteAccount(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        account.setDeleted(true);
        accountRepository.save(account);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        int number = 10000000 + random.nextInt(90000000); // 8 digit
        return String.valueOf(number);
    }

    private AccountDetailResponseDto mapToDto(Account account) {
        return AccountDetailResponseDto.builder()
                .accountId(account.getAccountId().toString())
                .userId(account.getUser().getUserId().toString())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .status(account.getStatus())
                .build();
    }
}
