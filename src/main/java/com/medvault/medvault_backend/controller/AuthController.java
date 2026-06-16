package com.medvault.medvault_backend.controller;

import com.medvault.medvault_backend.dto.LoginRequest;
import com.medvault.medvault_backend.dto.LoginResponse;
import com.medvault.medvault_backend.dto.RegisterRequest;
import com.medvault.medvault_backend.entity.User;
import com.medvault.medvault_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request,
            HttpSession session) {

        User user = authService.login(
                request.getEmail(),
                request.getPassword());

        session.setAttribute(
                "userEmail",
                user.getEmail());

        session.setAttribute(
                "role",
                user.getRole());

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
    @GetMapping("/me")
    public Object me(
            HttpSession session) {

        return session.getAttribute(
                "userEmail");
    }
    @PostMapping("/logout")
    public String logout(
            HttpSession session) {

        session.invalidate();

        return "Logged Out";
    }
}