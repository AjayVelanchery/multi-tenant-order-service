package com.neork.order_service.order.mapper;

import com.neork.order_service.order.command.CreateOrderCommand;
import com.neork.order_service.order.dto.CreateOrderRequestDto;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderCommandMapper {

    public CreateOrderCommand map(CreateOrderRequestDto dto) {
        return new CreateOrderCommand(
                dto.getTenantId(),
                dto.getAmount(),
                dto.getQuantity()
        );
    }
}
