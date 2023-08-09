package com.beetech.api_intern.features.user.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListUserRequestDto {
    private String userName;
    @Email(message = "loginId must be email")
    private String loginId;
    private Long totalPrice;
}
