package com.beetech.api_intern.features.user.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPasswordRequest {
    @Email(message = "invalid email")
    private String email;
}
