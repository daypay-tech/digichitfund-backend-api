package com.daypaytechnologies.digichitfund.app.organization.request;

import com.daypaytechnologies.digichitfund.infrastructure.data.ApiParameterError;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformApiDataValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrganizationRequestDataValidator {

    public void validateCreateOrg(final CreateOrganizationRequest request) {
        final List<ApiParameterError> parameterErrors = new ArrayList<>();
        if (request.getOrgName() == null || request.getOrgName().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("orgName", "OrgName parameter required");
            parameterErrors.add(apiParameterError);
        }
        throwValidationException(parameterErrors);
    }

    public void validateUpdateOrg(final UpdateOrganizationRequest request) {
        final List<ApiParameterError> parameterErrors = new ArrayList<>();
        if (request.getOrgName() == null || request.getOrgName().equals("")) {
            final ApiParameterError apiParameterError = new ApiParameterError("orgName", "OrgName parameter required");
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
