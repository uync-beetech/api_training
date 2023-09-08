package com.beetech.api_intern.features.orders;

import com.beetech.api_intern.features.orders.dto.CreateOrderRequest;
import com.beetech.api_intern.features.orders.dto.FindAllOrderRequest;
import com.beetech.api_intern.features.orders.dto.UpdateOrderRequest;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService {
    /**
     * Update order.
     *
     * @param request the request
     */
    void updateOrder(UpdateOrderRequest request);

    /**
     * Find all order list.
     *
     * @param request the request
     * @param page    the page
     * @param size    the size
     * @return the list
     */
    List<Order> findAllOrder(FindAllOrderRequest request, Integer page, Integer size);

    /**
     * Create order .
     *
     * @param request the request
     * @return the order
     */
    Order createOrder(CreateOrderRequest request);
}
