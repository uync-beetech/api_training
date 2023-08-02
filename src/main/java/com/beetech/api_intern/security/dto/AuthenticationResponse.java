package com.beetech.api_intern.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Authentication response.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    /**
     * accessToken String
     */
    private String loginId;
    private String accessToken;
    private String refreshToken;
    private String username;
}
