package com.aiops.auth_service.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aiops.auth_service.dto.*;
import com.aiops.auth_service.entity.User;
import com.aiops.auth_service.repository.UserRepository;
import com.aiops.auth_service.security.JwtService;
import com.aiops.auth_service.entity.Role;

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

    public String register(RegisterRequest request) {

    boolean exists =
            userRepository.existsByEmail(
                    request.getEmail());

    if (exists) {
        throw new RuntimeException(
                "Email already exists");
    }

    User user = new User();

    user.setName(request.getName());

    user.setEmail(request.getEmail());

    user.setPassword(
            passwordEncoder.encode(
                    request.getPassword())
    );

    user.setRole(Role.USER);

    userRepository.save(user);

    return "User registered successfully";
}

}
