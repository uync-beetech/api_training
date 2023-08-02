package com.beetech.api_intern.security;

import com.beetech.api_intern.security.dto.AuthenticationResponse;
import com.beetech.api_intern.security.dto.LoginDto;
import com.beetech.api_intern.security.dto.RefreshTokenDto;
import com.beetech.api_intern.security.dto.RegisterDto;
import com.beetech.api_intern.security.service.AuthService;
import com.beetech.api_intern.features.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The type Auth controller.
 */
@RestController
@RequestMapping("/api/v1/auth")
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
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenDto.getRefreshToken()));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {
        userService.register(registerDto);
        return new ResponseEntity<>("register success!", HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }
}
