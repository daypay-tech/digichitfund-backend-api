package com.daypaytechnologies.digichitfund.app.category.data;

import com.daypaytechnologies.digichitfund.infrastructure.data.ApiParameterError;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformApiDataValidationException;
import com.daypaytechnologies.digichitfund.app.category.request.CreateCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryDataValidator {

    public void validateCreateCategory(final CreateCategoryRequest request) {
        final List<ApiParameterError> parameterErrors = new ArrayList<>();
        if (request.getCategoryName() == null || request.getCategoryName().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("categoryName", "CategoryName parameter required");
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
