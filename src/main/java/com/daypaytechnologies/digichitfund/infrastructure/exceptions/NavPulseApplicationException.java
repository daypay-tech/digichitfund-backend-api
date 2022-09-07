package com.daypaytechnologies.digichitfund.infrastructure.exceptions;

public class NavPulseApplicationException extends AbstractPlatformException {

    public NavPulseApplicationException(String message) {
        super("error.msg.generic", message);
    }
}
