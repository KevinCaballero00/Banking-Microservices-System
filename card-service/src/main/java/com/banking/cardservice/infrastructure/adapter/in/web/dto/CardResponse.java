package com.banking.cardservice.infrastructure.adapter.in.web.dto;

import com.banking.cardservice.domain.model.CardStatus;
import com.banking.cardservice.domain.model.CardType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CardResponse(
        Long id,
        String maskedNumber,
        Long userId,
        String accountNumber,
        CardType type,
        CardStatus status,
        String cardHolderName,
        LocalDate expirationDate,
        BigDecimal creditLimit,
        LocalDateTime createdAt
) {}
