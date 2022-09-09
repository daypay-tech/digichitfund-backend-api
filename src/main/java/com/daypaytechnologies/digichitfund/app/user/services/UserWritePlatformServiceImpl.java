package com.daypaytechnologies.digichitfund.app.user.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.DuplicateRecordException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NavPulseApplicationException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;


import com.daypaytechnologies.digichitfund.app.user.constants.RoleConstants;
import com.daypaytechnologies.digichitfund.app.user.data.*;
import com.daypaytechnologies.digichitfund.app.user.domain.account.Account;
import com.daypaytechnologies.digichitfund.app.user.domain.account.AccountRepository;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUserRepository;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUserRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.user.domain.member.Member;
import com.daypaytechnologies.digichitfund.app.user.domain.role.Role;
import com.daypaytechnologies.digichitfund.app.user.domain.role.RoleRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.user.domain.member.MemberRepository;
import com.daypaytechnologies.digichitfund.app.user.domain.member.MemberRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.user.dto.AdministrationAuthTokenDTO;
import com.daypaytechnologies.digichitfund.app.user.request.AdministrationUserLoginRequest;
import com.daypaytechnologies.digichitfund.app.user.request.AdministrationUserSignUpRequest;
import com.daypaytechnologies.digichitfund.app.user.request.LoginRequest;
import com.daypaytechnologies.digichitfund.app.user.request.MemberSignUpRequest;
import com.daypaytechnologies.digichitfund.security.AdministrationUserDetailsImpl;
import com.daypaytechnologies.digichitfund.security.JwtTokenHandler;
import com.daypaytechnologies.digichitfund.security.MemberUserDetailsImpl;
import com.daypaytechnologies.digichitfund.security.authtoken.AdministrationUserUsernameAndPasswordAuthToken;
import com.daypaytechnologies.digichitfund.security.authtoken.MemberUsernameAndPasswordAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserWritePlatformServiceImpl implements UserWritePlatformService {



    private final PasswordEncoder passwordEncoder;

    private final RoleRepositoryWrapper roleRepositoryWrapper;

    private final UserDataValidator userDataValidator;



    private final JwtTokenHandler jwtTokenHandler;

    private final AuthenticationManager authenticationManager;



    private final AdministrationUserRepositoryWrapper administrationUserRepositoryWrapper;

    private final AdministrationUserRepository administrationUserRepository;

    private final AccountRepository accountRepository;



    private final AccountWritePlatformService accountWritePlatformService;


    @Override
    public Response doMemberSignUp(MemberSignUpRequest memberSignUpRequest) {
        return null;
    }

    @Override
    public Response doAdministrationUserSignUp(AdministrationUserSignUpRequest administrationUserSignUpRequest) {
        return null;
    }

    private void checkRoleHasSuperAdmin(List<Long> roles) {
        for(Long roleId: roles) {
            final Role role = roleRepositoryWrapper.findOneWithNotFoundDetection(roleId);
            if(role.getCode().equals(RoleConstants.ROLE_SUPER_ADMIN)) {
                throw new NavPulseApplicationException("You are not allowed to create super admin account");
            }
        }
    }

    private void checkOrgHasAdminAccount(Long orgId) {
        if(orgId == null) {
            throw new NavPulseApplicationException("Organization Id shouldn't be empty");
        }

    }



    @Override
    @Transactional
    public Response createSuperAdminAccount(AdministrationUserSignUpRequest administrationUserSignUpRequest) {
        try {
            final Account account = accountWritePlatformService.saveSuperAdminAccount(administrationUserSignUpRequest.getAccount());
            final AdministrationUser administrationUser = AdministrationUser.from(administrationUserSignUpRequest, account);
            this.administrationUserRepository.saveAndFlush(administrationUser);
            return Response.of(administrationUser.getId());
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            dve.getRootCause();
            handleDataIntegrityIssues(administrationUserSignUpRequest.getAccount().getMobile(), administrationUserSignUpRequest.getAccount().getEmail(), dve.getMostSpecificCause(), dve);
            return Response.empty();
        } catch (final PersistenceException dve) {
            Throwable throwable = ExceptionUtils.getRootCause(dve.getCause());
            handleDataIntegrityIssues(administrationUserSignUpRequest.getAccount().getMobile(), administrationUserSignUpRequest.getAccount().getEmail(), throwable, dve);
            return Response.empty();
        }
    }

    @Override
    public MemberAuthResponseData authenticateMember(LoginRequest loginRequest) {
        return null;
    }

    private void handleDataIntegrityIssues(String mobileNo, String emailId, final Throwable realCause, final Exception dve) {
        if (realCause.getMessage().contains("mobile")) {
            throw new PlatformDataIntegrityException("error.msg.client.duplicate.mobileNo",
                    "User with mobileNo `" + mobileNo + "` already exists", "mobileNo", mobileNo);
        }  else if (realCause.getMessage().contains("email")) {
            throw new PlatformDataIntegrityException("error.msg.client.duplicate.email",
                    "User with email `" + emailId + "` already exists", "email", emailId);
        }
        throw new PlatformDataIntegrityException("error.msg.client.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource.");
    }


    @Override
    public AdministrationUserAuthResponseData authenticateAdminUser(AdministrationUserLoginRequest loginRequest) {
        return null;
    }



}
