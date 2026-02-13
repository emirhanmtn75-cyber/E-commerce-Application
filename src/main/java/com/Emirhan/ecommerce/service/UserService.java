package com.Emirhan.ecommerce.service;

import com.Emirhan.ecommerce.dto.request.RegisterRequest;
import com.Emirhan.ecommerce.entity.User;
import com.Emirhan.ecommerce.enums.Role;
import com.Emirhan.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {

        // 1️⃣ email kontrolü
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // 🔥 ŞİFRE HASH
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.CUSTOMER);

        // 3️⃣ kaydet
        userRepository.save(user);
    }
}
