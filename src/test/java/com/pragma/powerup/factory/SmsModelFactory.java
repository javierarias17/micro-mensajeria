package com.pragma.powerup.factory;

import com.pragma.powerup.domain.model.SmsModel;

public class SmsModelFactory {

    private SmsModelFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static SmsModel createValidSmsModel() {
        return new SmsModel(10L, "123456");
    }
}
