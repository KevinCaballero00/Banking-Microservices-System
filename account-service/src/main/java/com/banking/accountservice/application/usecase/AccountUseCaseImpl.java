package com.banking.accountservice.application.usecase;

import com.banking.accountservice.domain.exception.AccountNotFoundException;
import com.banking.accountservice.domain.exception.InsufficientFundsException;
import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.domain.model.AccountStatus;
import com.banking.accountservice.domain.port.in.AccountUseCase;
import com.banking.accountservice.domain.port.out.AccountRepositoryPort;
import com.banking.accountservice.infrastructure.adapter.in.web.dto.AccountResponse;
import com.banking.accountservice.infrastructure.adapter.in.web.dto.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountUseCaseImpl implements AccountUseCase {

    private final AccountRepositoryPort accountRepository;

    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .userId(request.userId())
                .type(request.type())
                .status(AccountStatus.ACTIVE)
                .balance(request.initialBalance())
                .currency(request.currency())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return toResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse getAccountById(Long id) {
        return toResponse(findAccountById(id));
    }

    @Override
    public AccountResponse getAccountByNumber(String accountNumber) {
        return toResponse(accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber)));
    }

    @Override
    public List<AccountResponse> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId).stream()
                .map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public AccountResponse deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));
        Account updated = Account.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .userId(account.getUserId())
                .type(account.getType())
                .status(account.getStatus())
                .balance(account.getBalance().add(amount))
                .currency(account.getCurrency())
                .createdAt(account.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        return toResponse(accountRepository.save(updated));
    }

    @Override
    @Transactional
    public AccountResponse withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
        Account updated = Account.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .userId(account.getUserId())
                .type(account.getType())
                .status(account.getStatus())
                .balance(account.getBalance().subtract(amount))
                .currency(account.getCurrency())
                .createdAt(account.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        return toResponse(accountRepository.save(updated));
    }

    @Override
    @Transactional
    public void closeAccount(Long id) {
        Account account = findAccountById(id);
        Account closed = Account.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .userId(account.getUserId())
                .type(account.getType())
                .status(AccountStatus.CLOSED)
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .createdAt(account.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        accountRepository.save(closed);
    }

    private Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
    }

    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getUserId(),
                account.getType(),
                account.getStatus(),
                account.getBalance(),
                account.getCurrency(),
                account.getCreatedAt()
        );
    }
}
