package com.beetech.api_intern.features.products.productstatistic.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatisticsRequest {
    @Pattern(regexp = "^(20)\\d\\d(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$", message = "invalid date")
    private String date;

    @Min(value = 1)
    @Max(value = 53)
    private Integer week;

    @Min(value = 1)
    @Max(value = 12)
    private Integer month;

    @Min(value = 2000)
    private Integer year;
}
