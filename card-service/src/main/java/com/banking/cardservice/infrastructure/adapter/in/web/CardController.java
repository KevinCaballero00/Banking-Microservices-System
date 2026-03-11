package com.banking.cardservice.infrastructure.adapter.in.web;

import com.banking.cardservice.domain.port.in.CardUseCase;
import com.banking.cardservice.infrastructure.adapter.in.web.dto.CardResponse;
import com.banking.cardservice.infrastructure.adapter.in.web.dto.CreateCardRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardUseCase cardUseCase;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CreateCardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardUseCase.createCard(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable Long id) {
        return ResponseEntity.ok(cardUseCase.getCardById(id));
    }

    @GetMapping("/number/{cardNumber}")
    public ResponseEntity<CardResponse> getCardByNumber(@PathVariable String cardNumber) {
        return ResponseEntity.ok(cardUseCase.getCardByNumber(cardNumber));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CardResponse>> getCardsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cardUseCase.getCardsByUserId(userId));
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<CardResponse> blockCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardUseCase.blockCard(id));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<CardResponse> activateCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardUseCase.activateCard(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelCard(@PathVariable Long id) {
        cardUseCase.cancelCard(id);
        return ResponseEntity.noContent().build();
    }
}
