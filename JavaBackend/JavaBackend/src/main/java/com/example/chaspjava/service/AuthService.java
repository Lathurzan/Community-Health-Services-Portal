package com.example.chaspjava.service;

import com.example.chaspjava.dto.AuthResponse;
import com.example.chaspjava.dto.LoginRequest;
import com.example.chaspjava.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
