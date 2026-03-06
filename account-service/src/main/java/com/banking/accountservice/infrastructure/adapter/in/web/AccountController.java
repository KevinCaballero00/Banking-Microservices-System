package com.banking.accountservice.infrastructure.adapter.in.web;

import com.banking.accountservice.domain.port.in.AccountUseCase;
import com.banking.accountservice.infrastructure.adapter.in.web.dto.AccountResponse;
import com.banking.accountservice.infrastructure.adapter.in.web.dto.CreateAccountRequest;
import com.banking.accountservice.infrastructure.adapter.in.web.dto.DepositWithdrawRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountUseCase accountUseCase;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountUseCase.createAccount(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountUseCase.getAccountById(id));
    }

    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccountByNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountUseCase.getAccountByNumber(accountNumber));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(accountUseCase.getAccountsByUserId(userId));
    }

    @PostMapping("/number/{accountNumber}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable String accountNumber,
                                                   @Valid @RequestBody DepositWithdrawRequest request) {
        return ResponseEntity.ok(accountUseCase.deposit(accountNumber, request.amount()));
    }

    @PostMapping("/number/{accountNumber}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable String accountNumber,
                                                    @Valid @RequestBody DepositWithdrawRequest request) {
        return ResponseEntity.ok(accountUseCase.withdraw(accountNumber, request.amount()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> closeAccount(@PathVariable Long id) {
        accountUseCase.closeAccount(id);
        return ResponseEntity.noContent().build();
    }
}
