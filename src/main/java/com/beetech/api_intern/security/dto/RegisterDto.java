package com.beetech.api_intern.security.dto;

import jakarta.validation.constraints.NotBlank;
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
    @Size(min = 5, message = "{validation.loginId.size.too_short}")
    @Size(max = 50, message = "{validation.loginId.size.too_long}")
    private String loginId;

    /**
     * username String
     */
    @NotBlank
    @Size(min = 5, message = "{validation.username.size.too_short}")
    @Size(max = 50, message = "{validation.username.size.too_long}")
    private String username;

    /**
     * password String
     */
    @NotBlank
    @Size(min = 5, message = "{validation.password.size.too_short}")
    @Size(max = 50, message = "{validation.password.size.too_long}")
    private String password;

    @NotBlank
    private String birthday;
}
