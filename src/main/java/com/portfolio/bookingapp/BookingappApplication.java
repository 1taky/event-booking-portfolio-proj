package com.portfolio.bookingapp;

import com.portfolio.bookingapp.models.User;
import com.portfolio.bookingapp.models.enums.Roles;
import com.portfolio.bookingapp.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class BookingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingappApplication.class, args);
	}

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(userRepository.findByEmail("admin@test.com").isEmpty()) {
                User user =  new User();
                user.setRole(Roles.ROLE_ADMIN);
                user.setEmail("admin@test.com");
                user.setPassword(passwordEncoder.encode("123456"));
                userRepository.save(user);
            }
            if(userRepository.findByEmail("user@test.com").isEmpty()) {
                User user =  new User();
                user.setRole(Roles.ROLE_USER);
                user.setEmail("user@test.com");
                user.setPassword(passwordEncoder.encode("123456"));
                userRepository.save(user);
            }
        };
    }
}
