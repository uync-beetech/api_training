package com.beetech.api_intern.features.carts.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartRequest {
    /**
     * validate token: contain 20 chars
     */
    @Size(min = 20, max = 20, message = "token must contain 20 characters")
    private String token;

    /**
     * validate cart detail id: required, greater than or equal to 1
     */
    @NotNull(message = "id is required")
    @Min(value = 1, message = "id must be greater than or equal to 1")
    private Long id;

    /**
     * validate quantity: required, greater than or equal to 1
     */
    @NotNull(message = "quantity is required")
    @Min(value = 1, message = "quantity must be greater than or equal to 1")
    private Long quantity;

    /**
     * validate versionNo >= 1
     */
    @NotNull(message = "versionNo is required")
    @Min(value = 1, message = "versionNo must be greater than or equal to 1")
    private Long versionNo;
}
