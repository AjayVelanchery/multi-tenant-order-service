package com.neork.order_service.order.controller;

import com.neork.order_service.order.dto.CreateOrderRequestDto;
import com.neork.order_service.order.dto.CreateOrderResponseDto;
import com.neork.order_service.order.dto.OrderResponseDto;
import com.neork.order_service.order.service.OrderApplicationService;
import com.neork.order_service.order.service.OrderQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    private final OrderQueryService orderQueryService;

    public OrderController(OrderApplicationService orderApplicationService,
                           OrderQueryService orderQueryService) {
        this.orderApplicationService = orderApplicationService;
        this.orderQueryService = orderQueryService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(
            @RequestBody CreateOrderRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderApplicationService.createOrder(requestDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                orderQueryService.getOrderById(id)
        );
    }

}
