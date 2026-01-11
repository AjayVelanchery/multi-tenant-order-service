package com.neork.order_service.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderResponseDto {

    private Long id;
    private String tenantId;
    private BigDecimal amount;
    private int quantity;
    private String status;
    private String failureMessage;

}
