package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ISmsServicePort;
import com.pragma.powerup.domain.common.FieldConstants;
import com.pragma.powerup.domain.exception.FieldsValidationException;
import com.pragma.powerup.domain.exception.constant.FunctionalMessageConstants;
import com.pragma.powerup.domain.model.SmsModel;
import com.pragma.powerup.domain.spi.ISmsSenderPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import com.pragma.powerup.domain.validator.FieldValidator;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmsUseCase implements ISmsServicePort {

    private static final String MSG_ORDER_READY = "Your order is ready for pickup. Your security PIN is: ";

    private final ISmsSenderPort smsSenderPort;
    private final IUserServicePort userServicePort;

    public SmsUseCase(ISmsSenderPort smsSenderPort, IUserServicePort userServicePort) {
        this.smsSenderPort = smsSenderPort;
        this.userServicePort = userServicePort;
    }

    @Override
    public void sendSms(SmsModel smsModel) {
        this.validateFields(smsModel);
        smsSenderPort.send(userServicePort.getPhoneByCustomerId(smsModel.getCustomerId()),
                MSG_ORDER_READY + smsModel.getPin());
    }

    private void validateFields(SmsModel smsModel) {
        Map<String, String> errors = new LinkedHashMap<>();

        FieldValidator.validateNotNull(smsModel.getCustomerId(), FieldConstants.CUSTOMER_ID,
                FunctionalMessageConstants.CUSTOMER_ID_REQUIRED, errors);
        FieldValidator.validateNotBlank(smsModel.getPin(), FieldConstants.PIN,
                FunctionalMessageConstants.PIN_REQUIRED, errors);

        if (!errors.isEmpty())
            throw new FieldsValidationException(errors);
    }
}
