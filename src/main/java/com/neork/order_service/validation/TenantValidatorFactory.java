package com.neork.order_service.validation;

import com.neork.order_service.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TenantValidatorFactory {

    /**
     * Resolves tenant-specific validators using Spring's Map injection.
     * Key = tenantId, Value = TenantOrderValidator implementation.
     */

    private final Map<String, TenantOrderValidator> validators;

    public TenantValidatorFactory(Map<String, TenantOrderValidator> validators) {
        this.validators = validators;
    }

    public TenantOrderValidator getValidator(String tenantId) {
        TenantOrderValidator validator = validators.get(tenantId);

        if (validator == null) {
            throw new ValidationException("Invalid tenant");
        }
        return validator;
    }
}
