package com.medvault.medvault_backend.service;

import com.medvault.medvault_backend.dto.RegisterRequest;
import com.medvault.medvault_backend.entity.User;
import com.medvault.medvault_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .emergencyCode(generateEmergencyCode())
                .build();

        return userRepository.save(user);
    }

    private String generateEmergencyCode() {

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder("EMERG-");

        for (int i = 0; i < 12; i++) {

            sb.append(chars.charAt(random.nextInt(chars.length())));

            if ((i + 1) % 4 == 0 && i != 11) {
                sb.append("-");
            }
        }

        return sb.toString();
    }


    public User login(
            String email,
            String password) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid Email"));

        if (!passwordEncoder.matches(
                password,
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid Password");
        }

        return user;
    }
}