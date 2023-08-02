package com.beetech.api_intern.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * The type Login dto.
 */
@Data
@AllArgsConstructor
@ToString
public class LoginDto {
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
    private String password;
}
