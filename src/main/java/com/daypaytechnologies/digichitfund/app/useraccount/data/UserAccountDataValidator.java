package com.daypaytechnologies.digichitfund.app.useraccount.data;

import com.daypaytechnologies.digichitfund.app.useraccount.request.CreateUserAccountRequest;
import com.daypaytechnologies.digichitfund.infrastructure.data.ApiParameterError;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NavPulseApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserAccountDataValidator {

    public void validateSignUpFormData(final CreateUserAccountRequest memberSignUpRequest) {
        List<ApiParameterError> errorList = new ArrayList<>();
        if(memberSignUpRequest.getEmail() == null || memberSignUpRequest.getEmail().equals("")) {
            log.debug("User {} ", memberSignUpRequest.getEmail());
            final ApiParameterError apiError = new ApiParameterError("email", "email required");
            errorList.add(apiError);
        }
        if(memberSignUpRequest.getMobile() == null || memberSignUpRequest.getMobile().equals("")) {
            log.debug("User {} ", memberSignUpRequest.getMobile());
            final ApiParameterError apiError = new ApiParameterError("mobile", "mobile required");
            errorList.add(apiError);
        }
        if(memberSignUpRequest.getPassword() == null || memberSignUpRequest.getPassword().equals("")) {
            log.debug("User {} ", memberSignUpRequest.getPassword());
            final ApiParameterError apiError = new ApiParameterError("password", "password required");
            errorList.add(apiError);
        }
        this.checkAndThrowValidationError(errorList);
    }

    public void checkAndThrowValidationError(final List<ApiParameterError> validationErrors) {
        if(validationErrors != null && !validationErrors.isEmpty()) {
            throw new NavPulseApplicationException("Check for the mandatory fields and try again!");
        }
    }
}


