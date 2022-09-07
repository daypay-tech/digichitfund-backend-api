package com.daypaytechnologies.digichitfund.app.poll.data;

import com.daypaytechnologies.digichitfund.infrastructure.data.ApiParameterError;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformApiDataValidationException;
import com.daypaytechnologies.digichitfund.app.poll.request.CreatePollRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PollDataValidator {

    public void validateCreatePoll(final CreatePollRequest request) {
        final List<ApiParameterError> parameterErrors = new ArrayList<>();
        if (request.getPollName() == null || request.getPollName().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("pollName", "PollName parameter required");
            parameterErrors.add(apiParameterError);
        }
        if(request.getCategoryId() == null) {
            final ApiParameterError apiParameterError = new ApiParameterError("categoryId", "Category parameter required");
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
