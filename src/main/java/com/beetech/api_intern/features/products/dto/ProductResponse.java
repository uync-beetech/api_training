package com.beetech.api_intern.features.products.dto;

import com.beetech.api_intern.features.images.Image;
import com.beetech.api_intern.features.images.dto.ImageResponse;
import com.beetech.api_intern.features.products.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String sku;
    private String detailInfo;
    private Double price;
    private String imagePath;
    private String imageName;

    public ProductResponse(Product product) {
        setId(product.getId());
        setName(product.getName());
        setSku(product.getSku());
        setDetailInfo(product.getDetailInfo());
        setPrice(product.getPrice());

        Optional<Image> optionalImage = product.getThumbnailImage();
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            setImagePath(image.getPath());
            setImageName(image.getName());
        }
    }

    private List<ImageResponse> images;
}
