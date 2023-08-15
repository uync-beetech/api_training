package com.beetech.api_intern.features.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(
            regexp = "^(19|20)\\d\\d(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])-(19|20)\\d\\d(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$",
            message = "invalid birthday format YYYYMMDD-YYYYMMDD"
    )
    private String birthDay;
    private Long totalPrice;
}
