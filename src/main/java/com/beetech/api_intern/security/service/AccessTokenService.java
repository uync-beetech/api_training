package com.beetech.api_intern.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Jwt service.
 */
@Service
public class AccessTokenService extends JwtService {
    public AccessTokenService(
            @Value("${api.access.secretKey}") String secretKey,
            @Value("${api.access.expirationMs}") Long jwtExpirationMs
    ) {
        setSecretKey(secretKey);
        setJwtExpirationMs(jwtExpirationMs);
    }
}
