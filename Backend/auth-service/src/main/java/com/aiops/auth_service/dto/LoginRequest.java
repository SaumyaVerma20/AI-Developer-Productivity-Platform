package com.aiops.auth_service.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequest {

    @Email
    private String email;

    @NotBlank
    private String password;
}
