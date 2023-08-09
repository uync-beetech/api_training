package com.beetech.api_intern.features.user.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String loginId;
    private String username;
    private Long id;
    private String birthDay;
}
