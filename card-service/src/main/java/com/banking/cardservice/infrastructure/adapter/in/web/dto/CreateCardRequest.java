package com.banking.cardservice.infrastructure.adapter.in.web.dto;

import com.banking.cardservice.domain.model.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCardRequest(
        @NotNull Long userId,
        @NotBlank String accountNumber,
        @NotNull CardType type,
        @NotBlank String cardHolderName
) {}
