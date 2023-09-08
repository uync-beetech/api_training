package com.beetech.api_intern.features.district.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Find all district request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindAllDistrictRequest {
    @Pattern(regexp = "^[1-9]\\d*$", message = "cityId must be a number")
    private String cityId;
}
