package com.banking.transactionservice.infrastructure.adapter.out.client;

import com.banking.transactionservice.infrastructure.adapter.out.client.dto.AccountResponse;
import com.banking.transactionservice.infrastructure.adapter.out.client.dto.DepositWithdrawRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/api/accounts/number/{accountNumber}")
    AccountResponse getAccountByNumber(@PathVariable String accountNumber);

    @PostMapping("/api/accounts/number/{accountNumber}/deposit")
    AccountResponse deposit(@PathVariable String accountNumber,
                            @RequestBody DepositWithdrawRequest request);

    @PostMapping("/api/accounts/number/{accountNumber}/withdraw")
    AccountResponse withdraw(@PathVariable String accountNumber,
                             @RequestBody DepositWithdrawRequest request);
}
