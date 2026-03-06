package com.banking.transactionservice.domain.port.out;

import com.banking.transactionservice.domain.model.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionRepositoryPort {
    Transaction save(Transaction transaction);
    Optional<Transaction> findByReference(String reference);
    List<Transaction> findBySourceOrTargetAccount(String accountNumber);
}
