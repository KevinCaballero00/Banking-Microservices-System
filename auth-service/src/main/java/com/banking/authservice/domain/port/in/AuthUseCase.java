package com.banking.authservice.domain.port.in;

import com.banking.authservice.infrastructure.adapter.in.web.dto.AuthResponse;
import com.banking.authservice.infrastructure.adapter.in.web.dto.LoginRequest;
import com.banking.authservice.infrastructure.adapter.in.web.dto.RegisterRequest;

public interface AuthUseCase {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
}
