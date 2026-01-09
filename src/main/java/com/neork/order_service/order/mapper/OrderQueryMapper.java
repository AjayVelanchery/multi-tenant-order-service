package com.neork.order_service.order.mapper;

import com.neork.order_service.order.dto.OrderResponseDto;
import com.neork.order_service.order.model.Order;

public final class OrderQueryMapper {

    private OrderQueryMapper() {}

    public static OrderResponseDto toResponse(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getTenantId(),
                order.getAmount(),
                order.getQuantity(),
                order.getStatus().name()
        );
    }
}
