package com.daypaytechnologies.digichitfund.app.scheme.data;

import com.daypaytechnologies.digichitfund.app.scheme.request.CreateSchemeRequest;
import com.daypaytechnologies.digichitfund.infrastructure.data.ApiParameterError;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformApiDataValidationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchemeDataValidator {
    public void validateCreateScheme(final CreateSchemeRequest request) {
        final List<ApiParameterError> parameterErrors = new ArrayList<>();
        if (request.getSchemeName() == null || request.getSchemeName().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("schemeName", "Scheme Name parameter required");
            parameterErrors.add(apiParameterError);
        }
        if (request.getTotalAmount() == null || request.getTotalAmount().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("totalAmount", "Total Amount parameter required");
            parameterErrors.add(apiParameterError);
        }
        if (request.getTotalMembers() == null || request.getTotalMembers().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("totalMembers", "Total Member parameter required");
            parameterErrors.add(apiParameterError);
        }
        if (request.getCalendar() == null || request.getCalendar().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("calendarId", "Calendar parameter required");
            parameterErrors.add(apiParameterError);
        }
        if (request.getStartDate() == null || request.getStartDate().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("startDate", "Start Date parameter required");
            parameterErrors.add(apiParameterError);
        }
        if (request.getStartTime() == null || request.getStartTime().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("startTime", "Start Time parameter required");
            parameterErrors.add(apiParameterError);
        }
        throwValidationException(parameterErrors);

    }
    private void throwValidationException(final List<ApiParameterError> parameterErrors) {
        if (parameterErrors != null && parameterErrors.size() > 0) {
            throw new PlatformApiDataValidationException(parameterErrors);
        }
    }
}
