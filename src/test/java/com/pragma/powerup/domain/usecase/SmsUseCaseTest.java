package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.FieldsValidationException;
import com.pragma.powerup.domain.exception.NotFoundException;
import com.pragma.powerup.domain.exception.TechnicalException;
import com.pragma.powerup.domain.exception.constant.TechnicalMessageConstants;
import com.pragma.powerup.domain.model.SmsModel;
import com.pragma.powerup.domain.spi.ISmsSenderPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import com.pragma.powerup.factory.SmsModelFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmsUseCaseTest {

    private static final long CUSTOMER_ID = 10L;
    private static final String PHONE = "+573001234567";

    @Mock
    private ISmsSenderPort smsSenderPort;

    @Mock
    private IUserServicePort userServicePort;

    @InjectMocks
    private SmsUseCase smsUseCase;

    // ─── Happy path

    @Test
    void When_ValidModel_Expect_SmsSentWithCorrectPhoneAndMessage() {
        // Arrange
        SmsModel model = SmsModelFactory.createValidSmsModel();
        when(userServicePort.getPhoneByCustomerId(CUSTOMER_ID)).thenReturn(PHONE);

        // Act
        smsUseCase.sendSms(model);

        // Assert
        verify(userServicePort).getPhoneByCustomerId(CUSTOMER_ID);
        verify(smsSenderPort).send(anyString(), anyString());
    }

    // ─── Validation path

    @Test
    void Expect_FieldsValidationException_When_CustomerIdIsNull() {
        // Arrange
        SmsModel model = SmsModelFactory.createValidSmsModel();
        model.setCustomerId(null);

        // Act & Assert
        assertThrows(FieldsValidationException.class, () -> smsUseCase.sendSms(model));
        verify(smsSenderPort, never()).send(anyString(), anyString());
    }

    @Test
    void Expect_FieldsValidationException_When_PinIsNull() {
        // Arrange
        SmsModel model = SmsModelFactory.createValidSmsModel();
        model.setPin(null);

        // Act & Assert
        assertThrows(FieldsValidationException.class, () -> smsUseCase.sendSms(model));
        verify(smsSenderPort, never()).send(anyString(), anyString());
    }

    @Test
    void Expect_FieldsValidationException_When_PinIsBlank() {
        // Arrange
        SmsModel model = SmsModelFactory.createValidSmsModel();
        model.setPin("   ");

        // Act & Assert
        assertThrows(FieldsValidationException.class, () -> smsUseCase.sendSms(model));
        verify(smsSenderPort, never()).send(anyString(), anyString());
    }

    // ─── Exception path

    @Test
    void Expect_NotFoundException_When_CustomerDoesNotExist() {
        // Arrange
        SmsModel model = SmsModelFactory.createValidSmsModel();
        when(userServicePort.getPhoneByCustomerId(CUSTOMER_ID)).thenThrow(new NotFoundException());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> smsUseCase.sendSms(model));
        verify(smsSenderPort, never()).send(anyString(), anyString());
    }

    @Test
    void Expect_TechnicalException_When_UserServiceIsUnavailable() {
        // Arrange
        SmsModel model = SmsModelFactory.createValidSmsModel();
        when(userServicePort.getPhoneByCustomerId(anyLong()))
                .thenThrow(new TechnicalException(TechnicalMessageConstants.USUARIOS_UNAVAILABLE));

        // Act & Assert
        assertThrows(TechnicalException.class, () -> smsUseCase.sendSms(model));
        verify(smsSenderPort, never()).send(anyString(), anyString());
    }
}
