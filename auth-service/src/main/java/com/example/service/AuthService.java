package com.example.service;

import com.example.dto.JwtResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Transactional
    public JwtResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        if ("TEACHER".equalsIgnoreCase(request.getRole())) {
            user.setRole(Role.TEACHER);
        } else if ("ADMIN".equalsIgnoreCase(request.getRole())) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.STUDENT);
        }
        userRepository.save(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .roles(user.getRole().name())
                .build();

        String token = jwtService.generateToken(userDetails);

        return new JwtResponse(
                token,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name(),
                jwtExpiration
        );
    }

    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .roles(user.getRole().name())
                .build();

        String token = jwtService.generateToken(userDetails);

        return new JwtResponse(
                token,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name(),
                jwtExpiration
        );
    }
}