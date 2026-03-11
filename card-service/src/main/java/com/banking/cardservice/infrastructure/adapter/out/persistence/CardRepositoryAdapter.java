package com.banking.cardservice.infrastructure.adapter.out.persistence;

import com.banking.cardservice.domain.model.Card;
import com.banking.cardservice.domain.port.out.CardRepositoryPort;
import com.banking.cardservice.infrastructure.adapter.out.persistence.mapper.CardPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardRepositoryAdapter implements CardRepositoryPort {

    private final CardJpaRepository jpaRepository;
    private final CardPersistenceMapper mapper;

    @Override
    public Card save(Card card) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(card)));
    }

    @Override
    public Optional<Card> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Card> findByCardNumber(String cardNumber) {
        return jpaRepository.findByCardNumber(cardNumber).map(mapper::toDomain);
    }

    @Override
    public List<Card> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream().map(mapper::toDomain).toList();
    }
}
