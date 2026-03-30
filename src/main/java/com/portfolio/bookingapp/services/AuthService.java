package com.portfolio.bookingapp.services;

import com.portfolio.bookingapp.models.User;

public interface AuthService {
    String register(User user);
    String login(User user);
}
