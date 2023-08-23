package com.beetech.api_intern.features.orders.dto;

import com.beetech.api_intern.common.utils.DateTimeFormatterUtils;
import com.beetech.api_intern.features.orders.Order;
import com.beetech.api_intern.features.orders.orderdetail.dto.OrderDetailResponse;
import com.beetech.api_intern.features.orders.ordershippingdetail.OrderShippingDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponse {
    private Long id;
    private String displayId;
    private String username;
    private Double totalPrice;
    private String orderDate;
    private Integer orderStatus;
    private String shippingAddress;
    private String shippingDistrict;
    private String shippingCity;
    private List<OrderDetailResponse> details;

    public OrderResponse(Order order) {
        setId(order.getId());
        setDisplayId(order.getDisplayId());
        setUsername(order.getUser().getUsername());
        setOrderDate(DateTimeFormatterUtils.localDateToString(order.getOrderDate()));
        setOrderStatus(order.getStatus());

        OrderShippingDetail orderShippingDetail = order.getOrderShippingDetail();
        setShippingAddress(orderShippingDetail.getAddress());
        setShippingDistrict(orderShippingDetail.getDistrict().getName());
        setShippingCity(orderShippingDetail.getCity().getName());

        setTotalPrice(order.getTotalPrice());

        setDetails(order.getOrderDetails().stream().map(OrderDetailResponse::new).toList());
    }
}
