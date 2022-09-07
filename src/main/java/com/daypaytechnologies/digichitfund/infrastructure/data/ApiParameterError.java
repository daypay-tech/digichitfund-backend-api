package com.daypaytechnologies.digichitfund.infrastructure.data;

import lombok.Data;

@Data
public class ApiParameterError {

    private String parameterName;

    private String message;

    public ApiParameterError(String parameterName, String message) {
        this.parameterName = parameterName;
        this.message = message;
    }

}
