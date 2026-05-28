package com.aiops.auth_service.dto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String accessToken;

    private String tokenType;
}
