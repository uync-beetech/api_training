package com.beetech.api_intern.security.service;

import com.beetech.api_intern.security.dto.AuthenticationResponse;
import com.beetech.api_intern.security.dto.LoginDto;

public interface AuthService {
    AuthenticationResponse login(LoginDto request);

    AuthenticationResponse refreshToken(String refreshToken);

}
