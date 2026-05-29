package com.aiops.auth_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/register")
    public String register(
            @RequestBody String body
    ) {

        System.out.println(body);

        return "WORKING";
    }
}