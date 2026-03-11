package com.banking.cardservice.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CardJpaRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByCardNumber(String cardNumber);
    List<CardEntity> findByUserId(Long userId);
}
