package com.neork.order_service.order.command;


import com.neork.order_service.enums.AggregateType;
import com.neork.order_service.enums.OrderStatus;
import com.neork.order_service.enums.OutboxEventType;
import com.neork.order_service.enums.OutboxStatus;
import com.neork.order_service.order.model.Order;
import com.neork.order_service.order.repository.OrderRepository;
import com.neork.order_service.outbox.model.OutboxEvent;
import com.neork.order_service.outbox.repository.OutboxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOrderHandler {

    private final OrderRepository orderRepository;
    private final OutboxEventRepository outboxEventRepository;

    public CreateOrderHandler(OrderRepository orderRepository,
                              OutboxEventRepository outboxEventRepository) {
        this.orderRepository = orderRepository;
        this.outboxEventRepository = outboxEventRepository;
    }

    @Transactional
    public Long handle(CreateOrderCommand command) {

        Order order = new Order();
        order.setTenantId(command.getTenantId());
        order.setAmount(command.getAmount());
        order.setQuantity(command.getQuantity());



        order.setStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);




        OutboxEvent event = new OutboxEvent();
        event.setAggregateId(savedOrder.getId());
        event.setAggregateType(AggregateType.ORDER);
        event.setEventType(OutboxEventType.ORDER_CREATED);
        event.setStatus(OutboxStatus.PENDING);

        outboxEventRepository.save(event);

        return savedOrder.getId();

    }
}
