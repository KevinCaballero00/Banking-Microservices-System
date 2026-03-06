package com.banking.transactionservice.infrastructure.adapter.out.persistence;

import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.domain.port.out.TransactionRepositoryPort;
import com.banking.transactionservice.infrastructure.adapter.out.persistence.mapper.TransactionPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository jpaRepository;
    private final TransactionPersistenceMapper mapper;

    @Override
    public Transaction save(Transaction transaction) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(transaction)));
    }

    @Override
    public Optional<Transaction> findByReference(String reference) {
        return jpaRepository.findByTransactionReference(reference).map(mapper::toDomain);
    }

    @Override
    public List<Transaction> findBySourceOrTargetAccount(String accountNumber) {
        return jpaRepository.findBySourceOrTargetAccount(accountNumber)
                .stream().map(mapper::toDomain).toList();
    }
}
