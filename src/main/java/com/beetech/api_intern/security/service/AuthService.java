package com.beetech.api_intern.security.service;

import com.beetech.api_intern.security.dto.AuthenticationResponse;
import com.beetech.api_intern.security.dto.LoginRequest;

public interface AuthService {
    AuthenticationResponse login(LoginRequest request);

    AuthenticationResponse refreshToken(String refreshToken);

    void blockAllRefreshToken(Long userId);
}
