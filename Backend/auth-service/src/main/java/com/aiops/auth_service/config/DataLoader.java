package com.aiops.auth_service.config;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.aiops.auth_service.entity.User;
import com.aiops.auth_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByEmail(
                "admin@aiops.com"
        ).isEmpty()) {

            User user = User.builder()
                    .name("Admin User")
                    .email("admin@aiops.com")
                    .password(
                            passwordEncoder.encode(
                                    "admin123"
                            )
                    )
                    .role("ADMIN")
                    .build();

            userRepository.save(user);
        }
    }
    
}
