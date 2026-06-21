package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.SmsRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface ISmsRestControllerDocs {

    @Operation(summary = "Send SMS", description = "Sends an SMS message to a customer identified by clientId. Used internally by micro-plazoleta to notify the customer that their order is ready.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SMS sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body — clientId or pin is missing",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "Missing customerId", value = "{\"customerId\":\"Customer ID is required\"}"),
                                    @ExampleObject(name = "Missing pin", value = "{\"pin\":\"PIN is required\"}")
                            })),
            @ApiResponse(responseCode = "401", description = "Unauthorized — missing or invalid JWT token",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"No authentication token provided.\"}"))),
            @ApiResponse(responseCode = "403", description = "Forbidden — authenticated user does not have EMPLOYEE role",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"Access Denied\"}"))),
            @ApiResponse(responseCode = "500", description = "Internal server error or Twilio unavailable",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "{\"message\":\"An unexpected error occurred. Please contact the administrator.\"}")))
    })
    ResponseEntity<Void> sendSms(SmsRequestDto smsRequestDto);
}
