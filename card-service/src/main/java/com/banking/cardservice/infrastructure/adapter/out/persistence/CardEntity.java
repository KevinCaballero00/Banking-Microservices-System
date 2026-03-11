package com.banking.cardservice.infrastructure.adapter.out.persistence;

import com.banking.cardservice.domain.model.CardStatus;
import com.banking.cardservice.domain.model.CardType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    @Column(name = "masked_number", nullable = false)
    private String maskedNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "credit_limit", precision = 19, scale = 4)
    private BigDecimal creditLimit;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
