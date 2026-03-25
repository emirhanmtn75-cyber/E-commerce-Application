package com.Emirhan.ecommerce.controller;

import com.Emirhan.ecommerce.dto.response.AuthResponse;
import com.Emirhan.ecommerce.dto.request.LoginRequest;
import com.Emirhan.ecommerce.dto.request.RegisterRequest;
import com.Emirhan.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return new AuthResponse("User registered successfully");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return new AuthResponse(token);
    }

}
