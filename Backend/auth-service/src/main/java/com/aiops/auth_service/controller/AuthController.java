package com.aiops.auth_service.controller;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.aiops.auth_service.dto.*;
import com.aiops.auth_service.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    //  @PostMapping("/register")
    // public String register(
    //         @RequestBody String body
    // ) {
    //     System.out.println(body);
    //     return "WORKING";
    // }

    // @GetMapping("/test")
    // public String test() {
    //     System.out.println("REQUEST RECEIVED");
    //     return "APP WORKING";
    // }

    
    @PostMapping("/register")
    public String register(@Valid
            @RequestBody
            RegisterRequest request
    ) {
    System.out.println("Name: " + request.getName());
    System.out.println("Email: " + request.getEmail());
    System.out.println("Password: " + request.getPassword());
        return authService.register(request);
    }
    
    @PostMapping("/login")
    public AuthResponse login(@Valid
            @RequestBody
            LoginRequest request
    ) {

        return authService.login(request);
    }

    @PostMapping("/refresh")
    public TokenRefreshResponse refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public String logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request);
        return "Log out successful";
    }

    @GetMapping("/test")
    public String test() {
        return "Protected API reached";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin(){
       return "Admin access";
    }
}
