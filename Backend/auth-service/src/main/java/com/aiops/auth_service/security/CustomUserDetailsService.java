package com.aiops.auth_service.security;
import com.aiops.auth_service.entity.User;
import com.aiops.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;  
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

   @Override
public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {

    User user = userRepository
            .findByEmail(email)
            .orElseThrow(() ->
                    new UsernameNotFoundException(
                            "User not found"));

    return org.springframework.security.core.userdetails.User
            .builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities(
                    "ROLE_" + user.getRole().name())
            .build();
}
}