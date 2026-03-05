package com.revplay.service;

import com.revplay.dto.request.LoginRequest;
import com.revplay.dto.request.RegisterRequest;
import com.revplay.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}