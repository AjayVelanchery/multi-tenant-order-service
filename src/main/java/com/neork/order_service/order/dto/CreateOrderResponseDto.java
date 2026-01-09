package com.neork.order_service.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderResponseDto {

    private Long orderId;
    private String status;
}
