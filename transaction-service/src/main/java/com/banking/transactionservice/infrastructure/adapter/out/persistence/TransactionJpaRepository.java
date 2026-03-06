package com.banking.transactionservice.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {

    Optional<TransactionEntity> findByTransactionReference(String reference);

    @Query("SELECT t FROM TransactionEntity t WHERE t.sourceAccountNumber = :accountNumber OR t.targetAccountNumber = :accountNumber")
    List<TransactionEntity> findBySourceOrTargetAccount(@Param("accountNumber") String accountNumber);
}
