package com.banking.cardservice.domain.port.in;

import com.banking.cardservice.infrastructure.adapter.in.web.dto.CardResponse;
import com.banking.cardservice.infrastructure.adapter.in.web.dto.CreateCardRequest;
import java.util.List;

public interface CardUseCase {
    CardResponse createCard(CreateCardRequest request);
    CardResponse getCardById(Long id);
    CardResponse getCardByNumber(String cardNumber);
    List<CardResponse> getCardsByUserId(Long userId);
    CardResponse blockCard(Long id);
    CardResponse activateCard(Long id);
    void cancelCard(Long id);
}
