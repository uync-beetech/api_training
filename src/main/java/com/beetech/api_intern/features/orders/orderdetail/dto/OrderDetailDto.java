package com.beetech.api_intern.features.orders.orderdetail.dto;

import com.beetech.api_intern.features.images.Image;
import com.beetech.api_intern.features.orders.orderdetail.OrderDetail;
import com.beetech.api_intern.features.products.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Long id;
    private Long productId;
    private String productName;
    private String imagePath;
    private String imageName;
    private Long quantity;
    private Double price;
    private Double totalPrice;

    public OrderDetailDto(OrderDetail orderDetail) {
        setId(orderDetail.getId());
        Product product = orderDetail.getProduct();
        setProductId(product.getId());
        setProductName(product.getName());
        Image thumbnail = product.getThumbnailImage();
        setImagePath(thumbnail.getPath());
        setImageName(thumbnail.getName());
        setQuantity(orderDetail.getQuantity());
        setPrice(orderDetail.getPrice());
        setTotalPrice(orderDetail.getTotalPrice());
    }
}
