package com.daypaytechnologies.digichitfund.app.user.data;

import com.daypaytechnologies.digichitfund.infrastructure.data.ApiParameterError;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NavPulseApplicationException;
import com.daypaytechnologies.digichitfund.app.user.request.AdministrationUserSignUpRequest;
import com.daypaytechnologies.digichitfund.app.user.request.MemberSignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDataValidator {

    public void validateSignUpFormData(final MemberSignUpRequest memberSignUpRequest) {
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
        if(memberSignUpRequest.getGenderId() == null) {
            log.debug("User {} ", memberSignUpRequest.getGenderId());
            final ApiParameterError apiError = new ApiParameterError("gender", "gender required");
            errorList.add(apiError);
        }
        if(memberSignUpRequest.getFirstName() == null || memberSignUpRequest.getFirstName().equals("")) {
            log.debug("User {} ", memberSignUpRequest.getFirstName());
            final ApiParameterError apiError = new ApiParameterError("firstName", "firstName required");
            errorList.add(apiError);
        }
        if(memberSignUpRequest.getLastName() == null || memberSignUpRequest.getLastName().equals("")) {
            log.debug("User {} ", memberSignUpRequest.getLastName());
            final ApiParameterError apiError = new ApiParameterError("lastName", "lastName required");
            errorList.add(apiError);
        }
        this.checkAndThrowValidationError(errorList);
    }

    public void checkAndThrowValidationError(final List<ApiParameterError> validationErrors) {
        if(validationErrors != null && !validationErrors.isEmpty()) {
            throw new NavPulseApplicationException("Check for the mandatory fields and try again!");
        }
    }

    public void validateAdministrationSignUpFormData(final AdministrationUserSignUpRequest administrationSignUpRequest) {
        List<ApiParameterError> errorList = new ArrayList<>();
        if(administrationSignUpRequest.getAccount().getEmail() == null || administrationSignUpRequest.getAccount().getEmail().equals("")) {
            log.debug("User {} ", administrationSignUpRequest.getAccount().getEmail());
            final ApiParameterError apiError = new ApiParameterError("email", "email required");
            errorList.add(apiError);
        }
        if(administrationSignUpRequest.getAccount().getMobile() == null || administrationSignUpRequest.getAccount().getMobile().equals("")) {
            log.debug("User {} ", administrationSignUpRequest.getAccount().getMobile());
            final ApiParameterError apiError = new ApiParameterError("mobile", "mobile required");
            errorList.add(apiError);
        }
        if(administrationSignUpRequest.getOrgId() == null) {
            log.debug("User {} ", administrationSignUpRequest.getAccount().getEmail());
            final ApiParameterError apiError = new ApiParameterError("orgId", "organization Id required");
            errorList.add(apiError);
        }
        if(administrationSignUpRequest.getAccount().getPassword() == null || administrationSignUpRequest.getAccount().getPassword().equals("")) {
            log.debug("User {} ", administrationSignUpRequest.getAccount().getPassword());
            final ApiParameterError apiError = new ApiParameterError("password", "password required");
            errorList.add(apiError);
        }
        if(administrationSignUpRequest.getFirstName() == null || administrationSignUpRequest.getFirstName().equals("")) {
            log.debug("User {} ", administrationSignUpRequest.getFirstName());
            final ApiParameterError apiError = new ApiParameterError("firstName", "firstName required");
            errorList.add(apiError);
        }
        if(administrationSignUpRequest.getLastName() == null || administrationSignUpRequest.getLastName().equals("")) {
            log.debug("User {} ", administrationSignUpRequest.getLastName());
            final ApiParameterError apiError = new ApiParameterError("lastName", "lastName required");
            errorList.add(apiError);
        }
        if(administrationSignUpRequest.getAccount().getRoles() == null || administrationSignUpRequest.getAccount().getRoles().equals("")) {
            log.debug("User {} ", administrationSignUpRequest.getAccount().getRoles());
            final ApiParameterError apiError = new ApiParameterError("roles", "roles required");
            errorList.add(apiError);
        }
        this.checkAndThrowValidationError(errorList);
    }
}


