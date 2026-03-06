package com.banking.transactionservice.application.usecase;

import com.banking.transactionservice.domain.exception.TransactionFailedException;
import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.domain.model.TransactionStatus;
import com.banking.transactionservice.domain.model.TransactionType;
import com.banking.transactionservice.domain.port.in.TransactionUseCase;
import com.banking.transactionservice.domain.port.out.TransactionRepositoryPort;
import com.banking.transactionservice.infrastructure.adapter.in.web.dto.TransactionResponse;
import com.banking.transactionservice.infrastructure.adapter.in.web.dto.TransferRequest;
import com.banking.transactionservice.infrastructure.adapter.out.client.AccountClient;
import com.banking.transactionservice.infrastructure.adapter.out.client.dto.DepositWithdrawRequest;
import com.banking.transactionservice.infrastructure.adapter.out.messaging.TransactionEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionUseCaseImpl implements TransactionUseCase {

    private final TransactionRepositoryPort transactionRepository;
    private final AccountClient accountClient;
    private final TransactionEventPublisher eventPublisher;

    @Override
    @Transactional
    public TransactionResponse transfer(TransferRequest request) {
        String reference = "TXN" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();

        Transaction transaction = Transaction.builder()
                .transactionReference(reference)
                .sourceAccountNumber(request.sourceAccountNumber())
                .targetAccountNumber(request.targetAccountNumber())
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.PENDING)
                .amount(request.amount())
                .currency(request.currency())
                .description(request.description())
                .createdAt(LocalDateTime.now())
                .build();

        transaction = transactionRepository.save(transaction);

        try {
            accountClient.withdraw(request.sourceAccountNumber(),
                    new DepositWithdrawRequest(request.amount()));

            accountClient.deposit(request.targetAccountNumber(),
                    new DepositWithdrawRequest(request.amount()));

            transaction = Transaction.builder()
                    .id(transaction.getId())
                    .transactionReference(transaction.getTransactionReference())
                    .sourceAccountNumber(transaction.getSourceAccountNumber())
                    .targetAccountNumber(transaction.getTargetAccountNumber())
                    .type(transaction.getType())
                    .status(TransactionStatus.COMPLETED)
                    .amount(transaction.getAmount())
                    .currency(transaction.getCurrency())
                    .description(transaction.getDescription())
                    .createdAt(transaction.getCreatedAt())
                    .build();

            transaction = transactionRepository.save(transaction);
            eventPublisher.publishTransactionCompleted(transaction);

        } catch (Exception e) {
            log.error("Transaction failed: {}", e.getMessage());
            transaction = Transaction.builder()
                    .id(transaction.getId())
                    .transactionReference(transaction.getTransactionReference())
                    .sourceAccountNumber(transaction.getSourceAccountNumber())
                    .targetAccountNumber(transaction.getTargetAccountNumber())
                    .type(transaction.getType())
                    .status(TransactionStatus.FAILED)
                    .amount(transaction.getAmount())
                    .currency(transaction.getCurrency())
                    .description(transaction.getDescription())
                    .createdAt(transaction.getCreatedAt())
                    .build();
            transactionRepository.save(transaction);
            throw new TransactionFailedException("Transfer failed: " + e.getMessage());
        }

        return toResponse(transaction);
    }

    @Override
    public TransactionResponse getTransactionByReference(String reference) {
        return toResponse(transactionRepository.findByReference(reference)
                .orElseThrow(() -> new TransactionFailedException("Transaction not found: " + reference)));
    }

    @Override
    public List<TransactionResponse> getTransactionsByAccount(String accountNumber) {
        return transactionRepository.findBySourceOrTargetAccount(accountNumber)
                .stream().map(this::toResponse).toList();
    }

    private TransactionResponse toResponse(Transaction t) {
        return new TransactionResponse(
                t.getId(),
                t.getTransactionReference(),
                t.getSourceAccountNumber(),
                t.getTargetAccountNumber(),
                t.getType(),
                t.getStatus(),
                t.getAmount(),
                t.getCurrency(),
                t.getDescription(),
                t.getCreatedAt()
        );
    }
}
