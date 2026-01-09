package com.neork.order_service.validation;

import com.neork.order_service.exception.ValidationException;
import com.neork.order_service.order.model.Order;
import com.neork.order_service.validation.TenantOrderValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("TENANT_A")
public class TenantAValidator implements TenantOrderValidator {

    private static final BigDecimal MIN_AMOUNT =
            BigDecimal.valueOf(100);

    @Override
    public void validate(Order order) {
        if (order.getAmount().compareTo(MIN_AMOUNT) <= 0) {
            throw new ValidationException(
                    "Tenant A validation failed: amount must be > 100"
            );
        }
    }
}
