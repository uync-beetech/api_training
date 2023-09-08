package com.beetech.api_intern.features.carts.cartdetails;

import com.beetech.api_intern.features.images.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

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
        Optional<Image> optionalImage = cartDetail.getProduct().getThumbnailImage();
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            setImageName(image.getName());
            setImagePath(image.getPath());
        } else {
            setImageName(null);
            setImagePath(null);
        }
        setTotalPrice(cartDetail.getTotalPrice());
    }
}
