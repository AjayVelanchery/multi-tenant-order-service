package com.neork.order_service.order.service;

import com.neork.order_service.order.dto.OrderResponseDto;
import com.neork.order_service.order.mapper.OrderQueryMapper;
import com.neork.order_service.order.model.Order;
import com.neork.order_service.order.query.GetOrderQuery;
import com.neork.order_service.order.query.GetOrderQueryHandler;
import org.springframework.stereotype.Service;

@Service
public class OrderQueryService {

    private final GetOrderQueryHandler queryHandler;

    public OrderQueryService(GetOrderQueryHandler queryHandler) {
        this.queryHandler = queryHandler;
    }

    public OrderResponseDto getOrderById(Long orderId) {

        Order order = queryHandler.handle(
                new GetOrderQuery(orderId)
        );

        return OrderQueryMapper.toResponse(order);
    }
}

