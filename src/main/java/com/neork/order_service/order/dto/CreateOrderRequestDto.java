package com.neork.order_service.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderRequestDto {

    private String tenantId;
    private BigDecimal amount;
    private int quantity;
}
