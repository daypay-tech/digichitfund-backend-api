package com.daypaytechnologies.digichitfund.app.pollquestionoption.data;

import com.daypaytechnologies.digichitfund.infrastructure.data.ApiParameterError;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformApiDataValidationException;
import com.daypaytechnologies.digichitfund.app.pollquestionoption.request.CreateQuestionOptionRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PollQuestionOptionDataValidator {

    public void validateCreatePollQuestionOption(final List<CreateQuestionOptionRequest> options) {
        final List<ApiParameterError> parameterErrors = new ArrayList<>();
        if (options == null || options.isEmpty()) {
            final ApiParameterError apiParameterError = new ApiParameterError("option", "Option parameter required");
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
