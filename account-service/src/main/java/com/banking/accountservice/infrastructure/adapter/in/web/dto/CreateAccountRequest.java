package com.banking.accountservice.infrastructure.adapter.in.web.dto;

import com.banking.accountservice.domain.model.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotNull Long userId,
        @NotNull AccountType type,
        @NotNull @Positive BigDecimal initialBalance,
        @NotNull String currency
) {}
