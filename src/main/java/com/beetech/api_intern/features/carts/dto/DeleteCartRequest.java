package com.beetech.api_intern.features.carts.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Delete cart request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCartRequest {
    /**
     * validate token: contain 20 chars
     */
    @Size(min = 20, max = 20, message = "token must contain 20 characters")
    private String token;

    /**
     * validate clearCart value: 0 or 1
     */
    @Min(value = 0, message = "clearCart must be 0 or 1")
    @Max(value = 1, message = "clearCart must be 0 or 1")
    private Integer clearCart;

    /**
     * validate detailId >= 1
     */
    @Min(value = 1, message = "detailId must be greater than or equal to 1")
    private Long detailId;

    /**
     * validate versionNo >= 1
     */
    @NotNull(message = "versionNo is required")
    @Min(value = 1, message = "versionNo must be greater than or equal to 1")
    private Long versionNo;
}
