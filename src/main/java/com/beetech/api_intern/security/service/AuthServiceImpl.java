package com.beetech.api_intern.security.service;

import com.beetech.api_intern.common.exceptions.UnauthorizedException;
import com.beetech.api_intern.features.user.User;
import com.beetech.api_intern.features.user.UserRepository;
import com.beetech.api_intern.security.RefreshToken;
import com.beetech.api_intern.security.RefreshTokenRepository;
import com.beetech.api_intern.security.dto.AuthenticationResponse;
import com.beetech.api_intern.security.dto.LoginRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Auth service.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public AuthenticationResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getLoginId(),
                    request.getPassword()
            ));
            var user = userRepository.findUserByLoginId(request.getLoginId()).orElseThrow(()
                    -> new EntityNotFoundException("User not found!"));

            var accessToken = accessTokenService.generateToken(user);
            var refreshToken = refreshTokenService.generateToken(user);

            refreshTokenRepository.save(RefreshToken.builder().token(refreshToken).user(user).build());

            return AuthenticationResponse.builder()
                    .loginId(user.getLoginId())
                    .username(user.getUsername())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect!");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken) {
        String username = refreshTokenService.extractUsername(refreshToken);
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        boolean isValid = refreshTokenService.isTokenValid(refreshToken, user);
        var optionalRefreshToken = refreshTokenRepository.findByUserIdAndTokenAndBlockedIsFalse(user.getId(), refreshToken);
        if (!isValid || optionalRefreshToken.isEmpty()) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .accessToken(accessTokenService.generateToken(user))
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void blockAllRefreshToken(Long userId) {
        refreshTokenRepository.blockAllByUser(userId);
    }

}
