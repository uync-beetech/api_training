package com.beetech.api_intern.features.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePasswordDto {
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,32}$",
            message = "Old password must be between 8 and 32 characters, including letters, numbers, and uppercase letters."
    )
    String oldPassword;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,32}$",
            message = "Password must be between 8 and 32 characters, including letters, numbers, and uppercase letters."
    )
    String password;
}
