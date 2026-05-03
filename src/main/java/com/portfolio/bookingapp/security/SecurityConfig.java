package com.portfolio.bookingapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html" ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/events/**", "/api/venues/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/events/**", "/api/venues/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/events/**", "/api/venues/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/events/**", "/api/venues/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/bookings/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/bookings/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/bookings/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/bookings/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
