package com.neork.order_service.validation;

import com.neork.order_service.order.model.Order;

public interface TenantOrderValidator {

    void validate(Order order);
}
