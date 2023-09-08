package com.beetech.api_intern.security;

import com.beetech.api_intern.security.dto.AuthenticationResponse;
import com.beetech.api_intern.security.dto.LoginRequest;
import com.beetech.api_intern.security.dto.RefreshTokenRequest;
import com.beetech.api_intern.security.dto.RegisterRequest;
import com.beetech.api_intern.security.service.AuthService;
import com.beetech.api_intern.features.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * The type Auth controller.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    /**
     * inject auth service
     */
    private final AuthService authService;
    private final UserService userService;

    /**
     * Login response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Refresh token response entity.
     *
     * @param refreshTokenRequest the refresh token request
     * @return the response entity
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest.getRefreshToken()));
    }

    /**
     * Register response entity.
     *
     * @param registerRequest the register request
     * @return the response entity
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return new ResponseEntity<>("register success!", HttpStatus.CREATED);
    }

    /**
     * Test string.
     *
     * @return the string
     */
    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }
}
