package com.pragma.powerup.infrastructure.out.twilio;

import com.pragma.powerup.domain.spi.ISmsSenderPort;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioAdapter implements ISmsSenderPort {

    private final String fromPhoneNumber;

    public TwilioAdapter(String accountSid, String authToken, String fromPhoneNumber) {
        this.fromPhoneNumber = fromPhoneNumber;
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void send(String telefono, String mensaje) {
        Message.creator(
                new PhoneNumber(telefono),
                new PhoneNumber(fromPhoneNumber),
                mensaje
        ).create();
    }
}
