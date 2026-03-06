package com.banking.transactionservice.infrastructure.adapter.out.persistence.mapper;

import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.infrastructure.adapter.out.persistence.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionPersistenceMapper {
    TransactionEntity toEntity(Transaction transaction);
    Transaction toDomain(TransactionEntity entity);
}
