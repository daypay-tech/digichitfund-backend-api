package com.daypaytechnologies.digichitfund.app.pollquestion.data;

import com.daypaytechnologies.digichitfund.infrastructure.data.ApiParameterError;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformApiDataValidationException;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.CreatePollQuestionBulkRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.CreatePollQuestionRequest;
import com.daypaytechnologies.digichitfund.app.pollquestion.request.CreateQuestionRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PollQuestionDataValidator {

    public void validateCreatePollQuestion(final CreatePollQuestionRequest request) {
        final List<ApiParameterError> parameterErrors = new ArrayList<>();
        if(request.getPollId() == null) {
            final ApiParameterError apiParameterError = new ApiParameterError("pollId", "Poll parameter required");
            parameterErrors.add(apiParameterError);
        }
        if(request.getQuestion() == null) {
            final ApiParameterError apiParameterError = new ApiParameterError("question", "Question shouldn't be empty");
            parameterErrors.add(apiParameterError);
        }
        if(request.getQuestion() != null) {
            if(request.getQuestion().getOptions() == null
                    || request.getQuestion().getOptions().isEmpty()) {
                final ApiParameterError apiParameterError = new ApiParameterError("options", "Options shouldn't be empty");
                parameterErrors.add(apiParameterError);
            }
            if(request.getQuestion().getOptions().size() > 6) {
                final ApiParameterError apiParameterError = new ApiParameterError("options", "Options shouldn't be exceed 6");
                parameterErrors.add(apiParameterError);
            }
        }
        throwValidationException(parameterErrors);
    }

    public void validateCreateBulkPollQuestion(final CreatePollQuestionBulkRequest request) {
        final List<ApiParameterError> parameterErrors = new ArrayList<>();
        if(request.getPollId() == null) {
            final ApiParameterError apiParameterError = new ApiParameterError("pollId", "Poll parameter required");
            parameterErrors.add(apiParameterError);
        }
        if(request.getQuestions() == null || request.getQuestions().isEmpty()) {
            final ApiParameterError apiParameterError = new ApiParameterError("questions", "Questions shouldn't be empty");
            parameterErrors.add(apiParameterError);
        }
        List<CreateQuestionRequest> questionRequests = request.getQuestions();
        for(CreateQuestionRequest questionRequest: questionRequests) {
            if(questionRequest.getQuestion() == null) {
                final ApiParameterError apiParameterError = new ApiParameterError("Questions", "Questions shouldn't be empty");
                parameterErrors.add(apiParameterError);
            } else {
                if(questionRequest.getOptions() == null || questionRequest.getOptions().isEmpty()) {
                    final ApiParameterError apiParameterError = new ApiParameterError("options", "Options shouldn't be empty");
                    parameterErrors.add(apiParameterError);
                } else {
                    if(questionRequest.getOptions().size() > 6) {
                        final ApiParameterError apiParameterError = new ApiParameterError("options", "Options shouldn't be exceed 6");
                        parameterErrors.add(apiParameterError);
                    }
                }
            }
        }
        throwValidationException(parameterErrors);
    }
    private void throwValidationException(final List<ApiParameterError> parameterErrors) {
        if(parameterErrors != null && parameterErrors.size() > 0) {
            throw new PlatformApiDataValidationException(parameterErrors);
        }
    }
}
