package com.beetech.api_intern.features.orders;

import com.beetech.api_intern.features.orders.dto.CreateOrderDto;
import com.beetech.api_intern.features.orders.dto.FindAllOrderDto;
import com.beetech.api_intern.features.orders.dto.UpdateOrderDto;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrder(FindAllOrderDto dto);
    Order createOrder(CreateOrderDto dto);
    void updateOrder(UpdateOrderDto dto);
}
