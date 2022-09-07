package com.daypaytechnologies.digichitfund.app.pollanswer.data;

import com.daypaytechnologies.digichitfund.infrastructure.data.ApiParameterError;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformApiDataValidationException;
import com.daypaytechnologies.digichitfund.app.pollanswer.request.CreatePollAnswerRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PollAnswerDataValidator {

    public void validateCreatePollAnswer(final CreatePollAnswerRequest request) {
        final List<ApiParameterError> parameterErrors = new ArrayList<>();

        if(request.getQuestionId() == null || request.getQuestionId().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("questionId", "PollQuestion parameter required");
            parameterErrors.add(apiParameterError);
        }
        if(request.getOptionId() == null || request.getOptionId().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("optionId", "PollQuestionOption parameter required");
            parameterErrors.add(apiParameterError);
        }
        throwValidationException(parameterErrors);
    }

    private void throwValidationException(final List<ApiParameterError> parameterErrors) {
        if(parameterErrors != null && parameterErrors.size() > 0) {
            throw new PlatformApiDataValidationException(parameterErrors);
        }
    }
}
