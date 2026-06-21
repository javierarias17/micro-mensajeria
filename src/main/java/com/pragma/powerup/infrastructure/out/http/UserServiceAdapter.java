package com.pragma.powerup.infrastructure.out.http;

import com.pragma.powerup.domain.exception.NotFoundException;
import com.pragma.powerup.domain.exception.TechnicalException;
import com.pragma.powerup.domain.exception.constant.TechnicalMessageConstants;
import com.pragma.powerup.domain.spi.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
public class UserServiceAdapter implements IUserServicePort {

    private static final String ENDPOINT_USER_PHONE = "/api/v1/user/%s/phone";

    private final RestTemplate restTemplate;
    private final String usersServiceUrl;

    @Override
    public String getPhoneByCustomerId(Long customerId) {
        try {
            String url = usersServiceUrl + String.format(ENDPOINT_USER_PHONE, customerId);
            HttpEntity<Void> request = new HttpEntity<>(buildAuthHeaders());
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class).getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        } catch (ResourceAccessException e) {
            throw new TechnicalException(TechnicalMessageConstants.USUARIOS_UNAVAILABLE);
        }
    }

    @NonNull
    private HttpHeaders buildAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String authHeader = attributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader != null) {
                headers.set(HttpHeaders.AUTHORIZATION, authHeader);
            }
        }
        return headers;
    }
}
