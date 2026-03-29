package com.portfolio.bookingapp.services;

import com.portfolio.bookingapp.models.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String register(User user);
    String login(User user);
}
