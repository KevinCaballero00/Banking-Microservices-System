package com.banking.transactionservice.infrastructure.adapter.out.client.dto;

import java.math.BigDecimal;

public record AccountResponse(
        Long id,
        String accountNumber,
        Long userId,
        String type,
        String status,
        BigDecimal balance,
        String currency
) {}
