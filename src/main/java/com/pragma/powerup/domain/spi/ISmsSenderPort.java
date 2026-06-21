package com.pragma.powerup.domain.spi;

public interface ISmsSenderPort {
    void send(String telefono, String mensaje);
}
