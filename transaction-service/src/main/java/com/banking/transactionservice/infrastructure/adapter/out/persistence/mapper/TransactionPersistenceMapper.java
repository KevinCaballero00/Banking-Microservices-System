package com.banking.transactionservice.infrastructure.adapter.out.persistence.mapper;

import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.domain.model.TransactionStatus;
import com.banking.transactionservice.domain.model.TransactionType;
import com.banking.transactionservice.infrastructure.adapter.out.persistence.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionPersistenceMapper {

    public TransactionEntity toEntity(Transaction transaction) {
        if (transaction == null) return null;
        return TransactionEntity.builder()
                .id(transaction.getId())
                .transactionReference(transaction.getTransactionReference())
                .sourceAccountNumber(transaction.getSourceAccountNumber())
                .targetAccountNumber(transaction.getTargetAccountNumber())
                .type(transaction.getType())
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    public Transaction toDomain(TransactionEntity entity) {
        if (entity == null) return null;
        return Transaction.builder()
                .id(entity.getId())
                .transactionReference(entity.getTransactionReference())
                .sourceAccountNumber(entity.getSourceAccountNumber())
                .targetAccountNumber(entity.getTargetAccountNumber())
                .type(entity.getType())
                .status(entity.getStatus())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
