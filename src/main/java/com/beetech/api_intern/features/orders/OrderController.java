package com.beetech.api_intern.features.orders;

import com.beetech.api_intern.common.responses.CommonResponseBody;
import com.beetech.api_intern.features.orders.dto.CreateOrderRequest;
import com.beetech.api_intern.features.orders.dto.CreateOrderResponse;
import com.beetech.api_intern.features.orders.dto.FindAllOrderRequest;
import com.beetech.api_intern.features.orders.dto.UpdateOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("orders")
    public ResponseEntity<CommonResponseBody<List<Order>>> findOrder(
            @Valid @RequestBody FindAllOrderRequest request,
            @Param("page") Integer page,
            @Param("size") Integer size,
            @Param("sort") String sort
    ) {
        List<Order> data = orderService.findAllOrder(request, page, size);
        return ResponseEntity.ok(new CommonResponseBody<>(data));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("create-order")
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.ok(new CreateOrderResponse(order.getDisplayId(), order.getTotalPrice()));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("update-order")
    public ResponseEntity<Object> updateOrder(@Valid @RequestBody UpdateOrderRequest request) {
        orderService.updateOrder(request);
        return ResponseEntity.ok(new CommonResponseBody<>());
    }
}
