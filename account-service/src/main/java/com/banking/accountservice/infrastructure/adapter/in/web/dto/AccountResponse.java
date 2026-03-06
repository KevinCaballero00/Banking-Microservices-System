package com.banking.accountservice.infrastructure.adapter.in.web.dto;

import com.banking.accountservice.domain.model.AccountStatus;
import com.banking.accountservice.domain.model.AccountType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String accountNumber,
        Long userId,
        AccountType type,
        AccountStatus status,
        BigDecimal balance,
        String currency,
        LocalDateTime createdAt
) {}
