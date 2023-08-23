package com.beetech.api_intern.features.orders;

import com.beetech.api_intern.features.orders.dto.CreateOrderRequest;
import com.beetech.api_intern.features.orders.dto.FindAllOrderRequest;
import com.beetech.api_intern.features.orders.dto.UpdateOrderRequest;

import java.util.List;

public interface OrderService {
    void updateOrder(UpdateOrderRequest dto);

    List<Order> findAllOrder(FindAllOrderRequest dto);

    Order createOrder(CreateOrderRequest dto);
}
