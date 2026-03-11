package com.banking.cardservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private Long id;
    private String cardNumber;
    private String maskedNumber;
    private Long userId;
    private String accountNumber;
    private CardType type;
    private CardStatus status;
    private String cardHolderName;
    private LocalDate expirationDate;
    private BigDecimal creditLimit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
