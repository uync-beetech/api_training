package com.beetech.api_intern.features.orders.orderdetail.dto;

import com.beetech.api_intern.features.images.Image;
import com.beetech.api_intern.features.orders.orderdetail.OrderDetail;
import com.beetech.api_intern.features.products.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private Long productId;
    private String productName;
    private String imagePath;
    private String imageName;
    private Long quantity;
    private Double price;
    private Double totalPrice;

    public OrderDetailResponse(OrderDetail orderDetail) {
        setId(orderDetail.getId());
        Product product = orderDetail.getProduct();
        setProductId(product.getId());
        setProductName(product.getName());
        Optional<Image> optionalImage = product.getThumbnailImage();
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            setImagePath(image.getPath());
            setImageName(image.getName());
        }
        setQuantity(orderDetail.getQuantity());
        setPrice(orderDetail.getPrice());
        setTotalPrice(orderDetail.getTotalPrice());
    }
}