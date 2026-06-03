package com.aiops.auth_service.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiops.auth_service.dto.*;
import com.aiops.auth_service.entity.User;
import com.aiops.auth_service.entity.RefreshToken;
import com.aiops.auth_service.repository.UserRepository;
import com.aiops.auth_service.security.JwtService;
import com.aiops.auth_service.entity.Role;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword());
        if (!passwordMatches) {
            throw new RuntimeException("Invalid credentials");
        }
        String accessToken = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    public String register(RegisterRequest request) {
        boolean exists = userRepository.existsByEmail(request.getEmail());

        if (exists) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return "User registered successfully";
    }

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getEmail());
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());
                    return TokenRefreshResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(newRefreshToken.getToken())
                            .tokenType("Bearer")
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database or has been invalidated"));
    }

    public void logout(LogoutRequest request) {
        refreshTokenService.deleteByToken(request.getRefreshToken());
    }
}
