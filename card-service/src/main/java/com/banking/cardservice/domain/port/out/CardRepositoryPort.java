package com.banking.cardservice.domain.port.out;

import com.banking.cardservice.domain.model.Card;
import java.util.List;
import java.util.Optional;

public interface CardRepositoryPort {
    Card save(Card card);
    Optional<Card> findById(Long id);
    Optional<Card> findByCardNumber(String cardNumber);
    List<Card> findByUserId(Long userId);
}
