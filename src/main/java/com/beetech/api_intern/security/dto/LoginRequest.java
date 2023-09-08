package com.beetech.api_intern.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


/**
 * The type Login request.
 */
@Data
@AllArgsConstructor
@ToString
public class LoginRequest {
    /**
     * username String
     */
    @Size(min = 5, max = 255)
    @NotBlank(message = "loginId is required")
    @Email(message = "loginId must be email")
    private String loginId;

    /**
     * password String
     */
    @Size(min = 5, max = 50)
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,32}$",
            message = "Password must be between 8 and 32 characters, including letters, numbers, and uppercase letters."
    )
    private String password;
}
