package com.banking.authservice.infrastructure.adapter.in.web;

import com.banking.authservice.domain.port.in.AuthUseCase;
import com.banking.authservice.infrastructure.adapter.in.web.dto.AuthResponse;
import com.banking.authservice.infrastructure.adapter.in.web.dto.LoginRequest;
import com.banking.authservice.infrastructure.adapter.in.web.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authUseCase.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authUseCase.login(request));
    }
}
