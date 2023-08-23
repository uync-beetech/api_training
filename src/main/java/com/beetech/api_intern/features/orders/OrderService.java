package com.beetech.api_intern.features.orders;

import com.beetech.api_intern.features.orders.dto.CreateOrderRequest;
import com.beetech.api_intern.features.orders.dto.FindAllOrderRequest;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrder(FindAllOrderRequest dto);
    Order createOrder(CreateOrderRequest dto);
}
