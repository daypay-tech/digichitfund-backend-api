package com.daypaytechnologies.digichitfund.app.chart.data;

import com.daypaytechnologies.digichitfund.infrastructure.data.ApiParameterError;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformApiDataValidationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChartDataValidator {

    public void validateQuestionChartParameter(Long categoryId, Long pollId) {
        final List<ApiParameterError> parameterErrors = new ArrayList<>();
        if(categoryId == null) {
            final ApiParameterError apiParameterError = new ApiParameterError("categoryId", "CategoryId required");
            parameterErrors.add(apiParameterError);
        }
        if(pollId == null) {
            final ApiParameterError apiParameterError = new ApiParameterError("pollId", "PollId required");
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
