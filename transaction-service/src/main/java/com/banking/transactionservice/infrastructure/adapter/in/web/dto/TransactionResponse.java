package com.banking.transactionservice.infrastructure.adapter.in.web.dto;

import com.banking.transactionservice.domain.model.TransactionStatus;
import com.banking.transactionservice.domain.model.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String transactionReference,
        String sourceAccountNumber,
        String targetAccountNumber,
        TransactionType type,
        TransactionStatus status,
        BigDecimal amount,
        String currency,
        String description,
        LocalDateTime createdAt
) {}
