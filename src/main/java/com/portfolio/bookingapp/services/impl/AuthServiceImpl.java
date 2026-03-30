package com.portfolio.bookingapp.services.impl;

import com.portfolio.bookingapp.models.User;
import com.portfolio.bookingapp.models.enums.Roles;
import com.portfolio.bookingapp.repositories.UserRepository;
import com.portfolio.bookingapp.security.JwtService;
import com.portfolio.bookingapp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already exists";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Roles.ROLE_USER);
        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(User user) {
        return userRepository.findByEmail(user.getEmail())
                .filter(u -> passwordEncoder.matches(user.getPassword(), u.getPassword()))
                .map(u -> jwtService.generateJwtToken(u.getEmail()))
                .orElse("Invalid credentials");
    }
}
