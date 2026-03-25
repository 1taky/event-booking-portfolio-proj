package com.portfolio.bookingapp.services;

import com.portfolio.bookingapp.models.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<String> register(User user);
    ResponseEntity<String> login(User user);
}
