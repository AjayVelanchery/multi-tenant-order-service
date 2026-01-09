package com.neork.order_service.order.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CreateOrderCommand {

    private final String tenantId;
    private final BigDecimal amount;
    private final int quantity;
}
