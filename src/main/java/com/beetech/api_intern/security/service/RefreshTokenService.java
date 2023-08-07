package com.beetech.api_intern.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Jwt service.
 */
@Service
public class RefreshTokenService extends JwtService {

    public RefreshTokenService(
            @Value("${api.refresh.secretKey}") String secretKey,
            @Value("${api.refresh.expirationMs}") Long jwtExpirationMs
            ) {
        setSecretKey(secretKey);
        setJwtExpirationMs(jwtExpirationMs);
    }
}
