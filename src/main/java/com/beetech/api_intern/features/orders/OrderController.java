package com.beetech.api_intern.features.orders;

import com.beetech.api_intern.features.orders.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("orders")
    public ResponseEntity<List<OrderResponse>> findOrder(@Valid @RequestBody FindAllOrderRequest dto) {
        List<OrderResponse> data = orderService.findAllOrder(dto)
                .stream().map(OrderResponse::new)
                .toList();
        return ResponseEntity.ok(data);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("create-order")
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest dto) {
        Order order = orderService.createOrder(dto);
        return ResponseEntity.ok(new CreateOrderResponse(order.getDisplayId(), order.getTotalPrice()));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("update-order")
    public ResponseEntity<Object> updateOrder(@Valid @RequestBody UpdateOrderRequest request) {
        orderService.updateOrder(request);
        return ResponseEntity.ok().build();
    }
}
