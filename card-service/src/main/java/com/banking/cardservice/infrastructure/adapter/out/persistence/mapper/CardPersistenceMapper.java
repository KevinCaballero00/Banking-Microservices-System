package com.banking.cardservice.infrastructure.adapter.out.persistence.mapper;

import com.banking.cardservice.domain.model.Card;
import com.banking.cardservice.infrastructure.adapter.out.persistence.CardEntity;
import org.springframework.stereotype.Component;

@Component
public class CardPersistenceMapper {

    public CardEntity toEntity(Card card) {
        if (card == null) return null;
        return CardEntity.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .maskedNumber(card.getMaskedNumber())
                .userId(card.getUserId())
                .accountNumber(card.getAccountNumber())
                .type(card.getType())
                .status(card.getStatus())
                .cardHolderName(card.getCardHolderName())
                .expirationDate(card.getExpirationDate())
                .creditLimit(card.getCreditLimit())
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .build();
    }

    public Card toDomain(CardEntity entity) {
        if (entity == null) return null;
        return Card.builder()
                .id(entity.getId())
                .cardNumber(entity.getCardNumber())
                .maskedNumber(entity.getMaskedNumber())
                .userId(entity.getUserId())
                .accountNumber(entity.getAccountNumber())
                .type(entity.getType())
                .status(entity.getStatus())
                .cardHolderName(entity.getCardHolderName())
                .expirationDate(entity.getExpirationDate())
                .creditLimit(entity.getCreditLimit())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
