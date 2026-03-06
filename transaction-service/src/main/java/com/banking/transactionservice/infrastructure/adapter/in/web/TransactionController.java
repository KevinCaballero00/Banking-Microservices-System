package com.banking.transactionservice.infrastructure.adapter.in.web;

import com.banking.transactionservice.domain.port.in.TransactionUseCase;
import com.banking.transactionservice.infrastructure.adapter.in.web.dto.TransactionResponse;
import com.banking.transactionservice.infrastructure.adapter.in.web.dto.TransferRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionUseCase transactionUseCase;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionUseCase.transfer(request));
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<TransactionResponse> getByReference(@PathVariable String reference) {
        return ResponseEntity.ok(transactionUseCase.getTransactionByReference(reference));
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionResponse>> getByAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionUseCase.getTransactionsByAccount(accountNumber));
    }
}
