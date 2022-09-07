package com.daypaytechnologies.digichitfund.infrastructure.exceptions;

public class DuplicateRecordException extends AbstractPlatformException {

    public DuplicateRecordException(String message) {
        super("error.msg.duplicate.record", message);
    }
}
