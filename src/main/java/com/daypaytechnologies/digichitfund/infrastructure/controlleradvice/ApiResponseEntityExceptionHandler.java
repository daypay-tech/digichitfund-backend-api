package com.daypaytechnologies.digichitfund.infrastructure.controlleradvice;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.AbstractPlatformException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AbstractPlatformException.class)
    public ResponseEntity<ApiErrorInfo> applicationExceptionHandler(AbstractPlatformException ex) {
        ApiErrorHandler handler = new ApiErrorHandler();
        return handler.handleException(ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public String exception(AccessDeniedException e) {
        return "{\"status\":\"access denied\"}";
    }
}
