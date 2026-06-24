package com.pragma.powerup.infrastructure.out.http;

import com.pragma.powerup.domain.exception.NotFoundException;
import com.pragma.powerup.domain.exception.TechnicalException;
import com.pragma.powerup.domain.exception.constant.TechnicalMessageConstants;
import com.pragma.powerup.domain.spi.IUserServicePort;
import com.pragma.powerup.infrastructure.out.http.client.IUserServiceFeignClient;
import feign.FeignException;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceAdapter implements IUserServicePort {

    private final IUserServiceFeignClient feignClient;

    @Override
    public String getPhoneByCustomerId(Long customerId) {
        try {
            return feignClient.getPhoneByCustomerId(customerId);
        } catch (FeignException.NotFound e) {
            throw new NotFoundException();
        } catch (RetryableException e) {
            throw new TechnicalException(TechnicalMessageConstants.USUARIOS_UNAVAILABLE);
        }
    }
}
