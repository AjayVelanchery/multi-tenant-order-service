package com.neork.order_service.validation;

import com.neork.order_service.validation.TenantOrderValidator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TenantValidatorFactory {

    private final Map<String, TenantOrderValidator> validators;

    public TenantValidatorFactory(Map<String, TenantOrderValidator> validators) {
        this.validators = validators;
    }

    public TenantOrderValidator getValidator(String tenantId) {
        TenantOrderValidator validator = validators.get(tenantId);

        if (validator == null) {
            throw new IllegalArgumentException(
                    "No validator configured for tenant: " + tenantId
            );
        }
        return validator;
    }
}
