package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ISmsServicePort;
import com.pragma.powerup.domain.spi.ISmsSenderPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import com.pragma.powerup.domain.usecase.SmsUseCase;
import com.pragma.powerup.infrastructure.out.http.UserServiceAdapter;
import com.pragma.powerup.infrastructure.out.http.client.IUserServiceFeignClient;
import com.pragma.powerup.infrastructure.out.twilio.TwilioAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserServiceFeignClient userServiceFeignClient;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String phoneNumber;

    @Bean
    public IUserServicePort userServicePort() {
        return new UserServiceAdapter(userServiceFeignClient);
    }

    @Bean
    public ISmsSenderPort smsSenderPort() {
        return new TwilioAdapter(accountSid, authToken, phoneNumber);
    }

    @Bean
    public ISmsServicePort smsServicePort() {
        return new SmsUseCase(smsSenderPort(), userServicePort());
    }
}
