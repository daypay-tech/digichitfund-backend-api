package com.daypaytechnologies.digichitfund.infrastructure.exceptions;

public class MismatchQuestionAnswerException extends AbstractPlatformException {

    public MismatchQuestionAnswerException(String message) {
        super("error.msg.not.found", message);
    }
}
