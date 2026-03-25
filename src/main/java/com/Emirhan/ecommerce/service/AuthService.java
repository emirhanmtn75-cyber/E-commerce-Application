package com.Emirhan.ecommerce.service;

import com.Emirhan.ecommerce.dto.request.LoginRequest;
import com.Emirhan.ecommerce.dto.request.RegisterRequest;
import com.Emirhan.ecommerce.entity.User;
import com.Emirhan.ecommerce.enums.Role;
import com.Emirhan.ecommerce.repository.UserRepository;
import com.Emirhan.ecommerce.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // REGISTER
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);
    }

    // LOGIN
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

}
