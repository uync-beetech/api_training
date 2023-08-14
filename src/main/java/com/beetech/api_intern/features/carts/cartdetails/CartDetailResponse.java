package com.beetech.api_intern.features.carts.cartdetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailResponse {
    private Long id;
    private Long productId;
    private String imagePath;
    private String imageName;
    private Long quantity;
    private Double price;
    private Double totalPrice;

    public CartDetailResponse(CartDetail cartDetail) {
        setId(cartDetail.getId());
        setProductId(cartDetail.getProduct().getId());
        setQuantity(cartDetail.getQuantity());
        setPrice(cartDetail.getPrice());
        setImageName(cartDetail.getProduct().getImages().get(0).getName());
        setImagePath(cartDetail.getProduct().getImages().get(0).getPath());
        setTotalPrice(cartDetail.getTotalPrice());
    }
}
