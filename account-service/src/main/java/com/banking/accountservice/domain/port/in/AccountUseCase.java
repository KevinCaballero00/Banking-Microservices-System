package com.banking.accountservice.domain.port.in;

import com.banking.accountservice.infrastructure.adapter.in.web.dto.AccountResponse;
import com.banking.accountservice.infrastructure.adapter.in.web.dto.CreateAccountRequest;
import java.math.BigDecimal;
import java.util.List;

public interface AccountUseCase {
    AccountResponse createAccount(CreateAccountRequest request);
    AccountResponse getAccountById(Long id);
    AccountResponse getAccountByNumber(String accountNumber);
    List<AccountResponse> getAccountsByUserId(Long userId);
    AccountResponse deposit(String accountNumber, BigDecimal amount);
    AccountResponse withdraw(String accountNumber, BigDecimal amount);
    void closeAccount(Long id);
}
