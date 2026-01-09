package com.neork.order_service.validation;

import com.neork.order_service.exception.ValidationException;
import com.neork.order_service.order.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("TENANT_B")
public class TenantBValidator implements TenantOrderValidator {

    private static final BigDecimal MIN_AMOUNT =
            BigDecimal.valueOf(100);

    @Override
    public void validate(Order order) {
        if (order.getAmount().compareTo(MIN_AMOUNT) <= 0
                || order.getQuantity() <= 10) {
            throw new ValidationException(
                    "Tenant B validation failed: amount > 100 and quantity > 10 required"
            );
        }
    }
}
