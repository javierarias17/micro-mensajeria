package com.pragma.powerup.domain.validator;

import java.util.Map;

public class FieldValidator {

    private FieldValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validateNotBlank(String value, String field, String message,
                                        Map<String, String> errors) {
        if (value == null || value.isBlank())
            errors.put(field, message);
    }

    public static void validateNotNull(Object value, String field, String message,
                                       Map<String, String> errors) {
        if (value == null)
            errors.put(field, message);
    }

    public static void validatePositive(Number value, String field, String message,
                                        Map<String, String> errors) {
        if (value != null && value.doubleValue() <= 0)
            errors.put(field, message);
    }
}
