package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ISmsServicePort;
import com.pragma.powerup.domain.spi.ISmsSenderPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import com.pragma.powerup.domain.usecase.SmsUseCase;
import com.pragma.powerup.infrastructure.out.http.UserServiceAdapter;
import com.pragma.powerup.infrastructure.out.twilio.TwilioAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String phoneNumber;

    @Value("${adapter.micro-usuarios.url}")
    private String microUsuariosUrl;

    @Value("${adapter.micro-usuarios.timeout}")
    private int microUsuariosTimeout;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(microUsuariosTimeout);
        factory.setReadTimeout(microUsuariosTimeout);
        return new RestTemplate(factory);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserServiceAdapter(restTemplate(), microUsuariosUrl);
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
