package com.neork.order_service.saga;

import com.neork.order_service.enums.OrderStatus;
import com.neork.order_service.exception.ValidationException;
import com.neork.order_service.order.model.Order;
import com.neork.order_service.order.repository.OrderRepository;
import com.neork.order_service.validation.TenantOrderValidator;
import com.neork.order_service.validation.TenantValidatorFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderSagaProcessor {

    private final OrderRepository orderRepository;
    private final TenantValidatorFactory tenantValidatorFactory;

    public OrderSagaProcessor(OrderRepository orderRepository,
                              TenantValidatorFactory tenantValidatorFactory) {
        this.orderRepository = orderRepository;
        this.tenantValidatorFactory = tenantValidatorFactory;
    }

    @Transactional
    public void process(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Order not found for saga processing: " + orderId
                        )
                );

        try {

            TenantOrderValidator validator =
                    tenantValidatorFactory.getValidator(order.getTenantId());
            validator.validate(order);
            order.setStatus(OrderStatus.PROCESSED);

        } catch (ValidationException ex) {

            order.setStatus(OrderStatus.FAILED);
        }
    }
}
