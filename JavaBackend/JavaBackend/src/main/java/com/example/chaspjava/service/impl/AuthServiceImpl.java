package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.AuthResponse;
import com.example.chaspjava.dto.LoginRequest;
import com.example.chaspjava.dto.RegisterRequest;
import com.example.chaspjava.entity.Profile;
import com.example.chaspjava.repository.ProfileRepository;
import com.example.chaspjava.security.JwtTokenProvider;
import com.example.chaspjava.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final ProfileRepository repo;
    private final JwtTokenProvider jwt;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(ProfileRepository repo, JwtTokenProvider jwt) {
        this.repo = repo;
        this.jwt = jwt;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (repo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }

        Profile p = new Profile();
        p.setId(UUID.randomUUID().toString());
        p.setFullName(request.getFullName());
        p.setEmail(request.getEmail());
    // Convert incoming role string to enum (enum constants are lowercase)
    p.setRole(Profile.Role.valueOf(request.getRole().toLowerCase()));
    p.setPassword(passwordEncoder.encode(request.getPassword()));

        repo.save(p);

    String token = jwt.generateToken(p.getId(), p.getEmail(), p.getRole().name());

    return new AuthResponse(token, p.getId(), p.getEmail(), p.getRole().name());
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        Profile p = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

    if (!passwordEncoder.matches(request.getPassword(), p.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

    String token = jwt.generateToken(p.getId(), p.getEmail(), p.getRole().name());

    return new AuthResponse(token, p.getId(), p.getEmail(), p.getRole().name());
    }
}
