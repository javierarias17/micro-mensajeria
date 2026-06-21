package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.SmsModel;

public interface ISmsServicePort {
    void sendSms(SmsModel smsModel);
}
