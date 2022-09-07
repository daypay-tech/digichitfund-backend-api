package com.daypaytechnologies.digichitfund.infrastructure.controlleradvice;


import com.daypaytechnologies.digichitfund.infrastructure.exceptions.AbstractPlatformException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformApiDataValidationException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiErrorHandler {

    public ResponseEntity<ApiErrorInfo> handleException(AbstractPlatformException ex) {
        final ApiErrorInfo errorInfo = new ApiErrorInfo();
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        if(ex instanceof PlatformDataIntegrityException) {
            status = HttpStatus.CONFLICT;
            errorInfo.setStatusCode(HttpStatus.CONFLICT.value());
        }
        if(ex instanceof PlatformApiDataValidationException) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            PlatformApiDataValidationException platformApiDataValidationException = (PlatformApiDataValidationException) ex;
            errorInfo.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorInfo.setFields(platformApiDataValidationException.getErrors());
        }
        errorInfo.setErrorCode(ex.getGlobalisationMessageCode());
        errorInfo.setMessage(ex.getDefaultUserMessage());
        return new ResponseEntity<>(errorInfo, status);
    }
}
