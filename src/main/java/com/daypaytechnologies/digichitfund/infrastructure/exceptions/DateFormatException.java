package com.daypaytechnologies.digichitfund.infrastructure.exceptions;

public class DateFormatException extends AbstractPlatformException {

    public DateFormatException(String globalisationMessageCode, String defaultUserMessage) {
        super(globalisationMessageCode, defaultUserMessage);
    }

    public DateFormatException(String globalisationMessageCode, String defaultUserMessage, Throwable cause) {
        super(globalisationMessageCode, defaultUserMessage, cause);
    }

    public DateFormatException(String globalisationMessageCode, String defaultUserMessage, Object[] defaultUserMessageArgs) {
        super(globalisationMessageCode, defaultUserMessage, defaultUserMessageArgs);
    }
}
