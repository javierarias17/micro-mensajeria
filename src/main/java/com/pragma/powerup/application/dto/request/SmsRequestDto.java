package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SmsRequestDto {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "PIN is required")
    private String pin;
}
