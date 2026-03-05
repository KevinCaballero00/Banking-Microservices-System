package com.banking.authservice.domain.port.out;

import com.banking.authservice.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    User save(User user);
    boolean existsByEmail(String email);
}
