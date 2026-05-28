package com.aiops.auth_service.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aiops.auth_service.dto.*;
import com.aiops.auth_service.entity.User;
import com.aiops.auth_service.repository.UserRepository;
import com.aiops.auth_service.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword());
        if (!passwordMatches) {

            throw new RuntimeException(
                    "Invalid credentials");
        }
        String token = jwtService.generateToken(
                user.getEmail());

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }

}
