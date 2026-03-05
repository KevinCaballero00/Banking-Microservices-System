package com.banking.authservice.application.usecase;

import com.banking.authservice.domain.exception.InvalidCredentialsException;
import com.banking.authservice.domain.exception.UserAlreadyExistsException;
import com.banking.authservice.domain.model.Role;
import com.banking.authservice.domain.model.User;
import com.banking.authservice.domain.port.in.AuthUseCase;
import com.banking.authservice.domain.port.out.UserRepositoryPort;
import com.banking.authservice.infrastructure.adapter.in.web.dto.AuthResponse;
import com.banking.authservice.infrastructure.adapter.in.web.dto.LoginRequest;
import com.banking.authservice.infrastructure.adapter.in.web.dto.RegisterRequest;
import com.banking.authservice.infrastructure.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final UserRepositoryPort userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException(request.email());
        }
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .role(Role.USER)
                .active(true)
                .build();
        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved);
        return new AuthResponse(token, saved.getEmail(), saved.getRole().name());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }
}
