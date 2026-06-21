package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.SmsRequestDto;
import com.pragma.powerup.application.handler.ISmsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
public class SmsRestController implements ISmsRestControllerDocs {

    private final ISmsHandler smsHandler;

    @Override
    @PostMapping
    public ResponseEntity<Void> sendSms(@Valid @RequestBody SmsRequestDto smsRequestDto) {
        smsHandler.sendSms(smsRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
