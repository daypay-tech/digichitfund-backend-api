package com.daypaytechnologies.digichitfund.infrastructure.exceptions;

public class AuthenticationException extends AbstractPlatformException {

    public AuthenticationException(String message) {
        super("error.msg.user.not.logged.in", message);
    }
}
