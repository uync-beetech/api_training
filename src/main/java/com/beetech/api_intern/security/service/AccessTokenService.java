package com.beetech.api_intern.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Jwt service.
 */
@Service
public class AccessTokenService extends JwtService {
    public AccessTokenService(
            @Value("${chat_app.backend.access.secretKey}") String secretKey,
            @Value("${chat_app.backend.access.expirationMs}") Long jwtExpirationMs
    ) {
        setSecretKey(secretKey);
        setJwtExpirationMs(jwtExpirationMs);
    }
}
