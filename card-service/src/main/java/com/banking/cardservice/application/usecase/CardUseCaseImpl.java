package com.banking.cardservice.application.usecase;

import com.banking.cardservice.domain.exception.CardNotFoundException;
import com.banking.cardservice.domain.model.Card;
import com.banking.cardservice.domain.model.CardStatus;
import com.banking.cardservice.domain.model.CardType;
import com.banking.cardservice.domain.port.in.CardUseCase;
import com.banking.cardservice.domain.port.out.CardRepositoryPort;
import com.banking.cardservice.infrastructure.adapter.in.web.dto.CardResponse;
import com.banking.cardservice.infrastructure.adapter.in.web.dto.CreateCardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardUseCaseImpl implements CardUseCase {

    private final CardRepositoryPort cardRepository;

    @Override
    @Transactional
    public CardResponse createCard(CreateCardRequest request) {
        String cardNumber = generateCardNumber();
        String masked = "****-****-****-" + cardNumber.substring(12);

        Card card = Card.builder()
                .cardNumber(cardNumber)
                .maskedNumber(masked)
                .userId(request.userId())
                .accountNumber(request.accountNumber())
                .type(request.type())
                .status(CardStatus.ACTIVE)
                .cardHolderName(request.cardHolderName())
                .expirationDate(LocalDate.now().plusYears(4))
                .creditLimit(request.type() == CardType.CREDIT ? BigDecimal.valueOf(5000) : BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return toResponse(cardRepository.save(card));
    }

    @Override
    public CardResponse getCardById(Long id) {
        return toResponse(findById(id));
    }

    @Override
    public CardResponse getCardByNumber(String cardNumber) {
        return toResponse(cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException("Card not found: " + cardNumber)));
    }

    @Override
    public List<CardResponse> getCardsByUserId(Long userId) {
        return cardRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public CardResponse blockCard(Long id) {
        return toResponse(updateStatus(id, CardStatus.BLOCKED));
    }

    @Override
    @Transactional
    public CardResponse activateCard(Long id) {
        return toResponse(updateStatus(id, CardStatus.ACTIVE));
    }

    @Override
    @Transactional
    public void cancelCard(Long id) {
        updateStatus(id, CardStatus.CANCELLED);
    }

    private Card findById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));
    }

    private Card updateStatus(Long id, CardStatus newStatus) {
        Card card = findById(id);
        Card updated = Card.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .maskedNumber(card.getMaskedNumber())
                .userId(card.getUserId())
                .accountNumber(card.getAccountNumber())
                .type(card.getType())
                .status(newStatus)
                .cardHolderName(card.getCardHolderName())
                .expirationDate(card.getExpirationDate())
                .creditLimit(card.getCreditLimit())
                .createdAt(card.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        return cardRepository.save(updated);
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("4"); // Visa prefix
        for (int i = 0; i < 15; i++) sb.append(random.nextInt(10));
        return sb.toString();
    }

    private CardResponse toResponse(Card card) {
        return new CardResponse(
                card.getId(), card.getMaskedNumber(), card.getUserId(),
                card.getAccountNumber(), card.getType(), card.getStatus(),
                card.getCardHolderName(), card.getExpirationDate(),
                card.getCreditLimit(), card.getCreatedAt()
        );
    }
}
