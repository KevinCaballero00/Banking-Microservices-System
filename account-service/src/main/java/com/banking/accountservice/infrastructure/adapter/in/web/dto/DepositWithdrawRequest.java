package com.banking.accountservice.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record DepositWithdrawRequest(
        @NotNull @Positive BigDecimal amount
) {}
