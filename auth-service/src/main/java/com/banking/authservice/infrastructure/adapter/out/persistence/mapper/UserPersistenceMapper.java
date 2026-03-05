package com.banking.authservice.infrastructure.adapter.out.persistence.mapper;

import com.banking.authservice.domain.model.User;
import com.banking.authservice.infrastructure.adapter.out.persistence.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
}
