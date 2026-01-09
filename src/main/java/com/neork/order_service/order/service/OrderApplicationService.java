package com.neork.order_service.order.service;

import com.neork.order_service.enums.OrderStatus;
import com.neork.order_service.order.command.CreateOrderCommand;
import com.neork.order_service.order.command.CreateOrderHandler;
import com.neork.order_service.order.dto.CreateOrderRequestDto;
import com.neork.order_service.order.dto.CreateOrderResponseDto;
import com.neork.order_service.order.mapper.CreateOrderCommandMapper;
import org.springframework.stereotype.Service;
@Service
public class OrderApplicationService {

    private final CreateOrderHandler handler;
    private final CreateOrderCommandMapper mapper;

    public OrderApplicationService(CreateOrderHandler handler,
                                   CreateOrderCommandMapper mapper) {
        this.handler = handler;
        this.mapper = mapper;
    }

    public CreateOrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        CreateOrderCommand command = mapper.map(requestDto);

        Long orderId = handler.handle(command);

        return new CreateOrderResponseDto(
                orderId,
                OrderStatus.PENDING.name()
        );
    }
}
