package com.aiops.auth_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.aiops.auth_service.entity.User;
import com.aiops.auth_service.entity.Role;
import com.aiops.auth_service.repository.UserRepository;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.count() == 0) {
				User defaultUser = new User();
				defaultUser.setName("Saumya Verma");
				defaultUser.setEmail("admin@ops.com");
				defaultUser.setPassword(passwordEncoder.encode("password123"));
				defaultUser.setRole(Role.ADMIN);
				userRepository.save(defaultUser);
				System.out.println("Seeded database with default user: admin@ops.com / password123");
			}
		};
	}
}
