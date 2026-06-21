package com.pragma.powerup.domain.exception.constant;

public class FunctionalMessageConstants {

    private FunctionalMessageConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String BUSINESS_VALIDATION_FAILED = "Business validation failed";
    public static final String CUSTOMER_ID_REQUIRED = "Customer ID is required";
    public static final String PIN_REQUIRED = "PIN is required";
}
