package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.SmsRequestDto;

public interface ISmsHandler {
    void sendSms(SmsRequestDto smsRequestDto);
}
