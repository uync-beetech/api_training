package com.beetech.api_intern.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 * The type Register dto.
 */
@ToString
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RegisterDto {
    @NotBlank
    @Email(message = "loginId must be email")
    private String loginId;

    /**
     * username String
     */
    @NotBlank
    @Size(min = 5, message = "{validation.username.size.too_short}")
    @Size(max = 255, message = "{validation.username.size.too_long}")
    private String username;

    /**
     * password String
     */
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,32}$",
            message = "Password must be between 8 and 32 characters, including letters, numbers, and uppercase letters."
    )
    private String password;

    @NotBlank
    @Pattern(
            regexp = "^(?:19|20)\\d\\d(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$",
            message = "invalid birthday format YYYYMMDD"
    )
    private String birthday;
}
