package com.banking.transactionservice.infrastructure.adapter.out.client;

import com.banking.transactionservice.infrastructure.adapter.out.client.dto.AccountResponse;
import com.banking.transactionservice.infrastructure.adapter.out.client.dto.DepositWithdrawRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AccountClient {

    @Value("${services.account-service.url}")
    private String accountServiceUrl;

    public AccountResponse getAccountByNumber(String accountNumber) {
        return RestClient.create()
                .get()
                .uri(accountServiceUrl + "/api/accounts/number/" + accountNumber)
                .retrieve()
                .body(AccountResponse.class);
    }

    public AccountResponse deposit(String accountNumber, DepositWithdrawRequest request) {
        return RestClient.create()
                .post()
                .uri(accountServiceUrl + "/api/accounts/number/" + accountNumber + "/deposit")
                .body(request)
                .retrieve()
                .body(AccountResponse.class);
    }

    public AccountResponse withdraw(String accountNumber, DepositWithdrawRequest request) {
        return RestClient.create()
                .post()
                .uri(accountServiceUrl + "/api/accounts/number/" + accountNumber + "/withdraw")
                .body(request)
                .retrieve()
                .body(AccountResponse.class);
    }
}
