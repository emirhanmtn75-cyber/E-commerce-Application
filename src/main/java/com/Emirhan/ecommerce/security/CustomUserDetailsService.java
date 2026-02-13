package com.Emirhan.ecommerce.security;

import com.Emirhan.ecommerce.entity.User;
import com.Emirhan.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
private final UserRepository userRepository;
@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User  user=userRepository.findByEmail(email);
    if(user==null){
        throw new UsernameNotFoundException(email);
    }
    return  org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRole().name())
            .build();
}
}
