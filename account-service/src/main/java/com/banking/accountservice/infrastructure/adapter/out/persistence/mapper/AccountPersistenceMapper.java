package com.banking.accountservice.infrastructure.adapter.out.persistence.mapper;

import com.banking.accountservice.domain.model.Account;
import com.banking.accountservice.infrastructure.adapter.out.persistence.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountPersistenceMapper {
    AccountEntity toEntity(Account account);
    Account toDomain(AccountEntity entity);
}
