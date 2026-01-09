package com.neork.order_service.order.query;

import com.neork.order_service.exception.ResourceNotFoundException;
import com.neork.order_service.order.model.Order;
import com.neork.order_service.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class GetOrderQueryHandler {

    private final OrderRepository orderRepository;

    public GetOrderQueryHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order handle(GetOrderQuery query) {
        return orderRepository.findById(query.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id " + query.getOrderId()
                        ));
    }
}
