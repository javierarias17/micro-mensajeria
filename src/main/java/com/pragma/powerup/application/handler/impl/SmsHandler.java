package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.SmsRequestDto;
import com.pragma.powerup.application.handler.ISmsHandler;
import com.pragma.powerup.application.mapper.ISmsRequestMapper;
import com.pragma.powerup.domain.api.ISmsServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsHandler implements ISmsHandler {

    private final ISmsServicePort smsServicePort;
    private final ISmsRequestMapper smsRequestMapper;

    @Override
    public void sendSms(SmsRequestDto smsRequestDto) {
        smsServicePort.sendSms(smsRequestMapper.toModel(smsRequestDto));
    }
}
