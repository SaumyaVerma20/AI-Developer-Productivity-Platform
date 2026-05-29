package com.aiops.auth_service.controller;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import com.aiops.auth_service.dto.*;
import com.aiops.auth_service.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public String register(
            @RequestBody
            RegisterRequest request
    ) {
        return authService.register(request);
    }
    
    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody
            LoginRequest request
    ) {

        return authService.login(request);
    }
}
