package com.daypaytechnologies.digichitfund.app.user.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.DuplicateRecordException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NavPulseApplicationException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;
import com.daypaytechnologies.digichitfund.master.age.domain.Age;
import com.daypaytechnologies.digichitfund.master.age.domain.AgeRepositoryWrapper;
import com.daypaytechnologies.digichitfund.master.gender.domain.Gender;
import com.daypaytechnologies.digichitfund.master.gender.domain.GenderRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.organization.domain.Organization;
import com.daypaytechnologies.digichitfund.app.organization.domain.OrganizationRepositoryWrapper;
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

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepositoryWrapper roleRepositoryWrapper;

    private final UserDataValidator userDataValidator;

    private final GenderRepositoryWrapper genderRepositoryWrapper;

    private final AgeRepositoryWrapper ageRepositoryWrapper;

    private final JwtTokenHandler jwtTokenHandler;

    private final AuthenticationManager authenticationManager;

    private final MemberRepositoryWrapper memberRepositoryWrapper;

    private final AdministrationUserRepositoryWrapper administrationUserRepositoryWrapper;

    private final AdministrationUserRepository administrationUserRepository;

    private final AccountRepository accountRepository;

    private final OrganizationRepositoryWrapper organizationRepositoryWrapper;

    private final AccountWritePlatformService accountWritePlatformService;

    @Override
    @Transactional
    public Response doMemberSignUp(MemberSignUpRequest memberSignUpRequest) {
        try {
            this.userDataValidator.validateSignUpFormData(memberSignUpRequest);
            final Gender gender = this.genderRepositoryWrapper.findOneWithNotFoundDetection(memberSignUpRequest.getGenderId());
            final Age age = this.ageRepositoryWrapper.findOneWithNotFoundDetection(memberSignUpRequest.getAgeId());
            final Member member = Member.from(memberSignUpRequest, gender, age, passwordEncoder);
            this.memberRepository.saveAndFlush(member);
            return Response.of(member.getId());
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            dve.getRootCause();
            handleDataIntegrityIssues(memberSignUpRequest.getMobile(), memberSignUpRequest.getEmail(), dve.getMostSpecificCause(), dve);
            return Response.empty();
        } catch (final PersistenceException dve) {
            Throwable throwable = ExceptionUtils.getRootCause(dve.getCause());
            handleDataIntegrityIssues(memberSignUpRequest.getMobile(), memberSignUpRequest.getEmail(), throwable, dve);
            return Response.empty();
        }
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
        final AdministrationUser adminOrg = this.administrationUserRepository.findByOrg(orgId);
        if(adminOrg != null) {
            for(Role role: adminOrg.getAccount().getRoles()) {
                if(role.getCode().equals(RoleConstants.ROLE_ADMIN)) {
                    throw new NavPulseApplicationException("Admin account already exist for this organization");
                }
            }
        }
    }

    @Override
    @Transactional
    public Response doAdministrationUserSignUp(AdministrationUserSignUpRequest administrationUserSignUpRequest) {
        try {
            this.userDataValidator.validateAdministrationSignUpFormData(administrationUserSignUpRequest);
            this.checkRoleHasSuperAdmin(administrationUserSignUpRequest.getAccount().getRoles());
            final Organization organization = this.organizationRepositoryWrapper.findOneWithNotFoundDetection(administrationUserSignUpRequest.getOrgId());
            String userName = administrationUserSignUpRequest.getAccount().getEmail();
            this.checkOrgHasAdminAccount(administrationUserSignUpRequest.getOrgId());
            final AdministrationUser oldAdministrationUser = this.administrationUserRepository.findByEmailOrMobile(userName, organization.getId());
            if(oldAdministrationUser != null) {
                throw new DuplicateRecordException("Account with this email for this organization exist");
            }
            final Account account = accountWritePlatformService.saveAccount(administrationUserSignUpRequest.getAccount());
            final AdministrationUser administrationUser = AdministrationUser.from(administrationUserSignUpRequest, account, organization);
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
    @Transactional
    public Response createSuperAdminAccount(AdministrationUserSignUpRequest administrationUserSignUpRequest) {
        try {
            final Account account = accountWritePlatformService.saveSuperAdminAccount(administrationUserSignUpRequest.getAccount());
            final AdministrationUser administrationUser = AdministrationUser.from(administrationUserSignUpRequest, account, null);
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
    public MemberAuthResponseData authenticateMember(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new MemberUsernameAndPasswordAuthToken(loginRequest.getUserName().trim(),
                        loginRequest.getPassword().trim()));
        MemberUserDetailsImpl userDetails = (MemberUserDetailsImpl) authentication.getPrincipal();
        final Member member = this.memberRepositoryWrapper.findOneWithNotFoundDetection(userDetails.getId());
        String jwtToken = jwtTokenHandler.generateJwtToken(userDetails.getUsername(), member.getId());
        JwtResponse jwtResponse =  new JwtResponse(jwtToken);
        MemberAuthResponseData memberAuthResponseData = MemberAuthResponseData
                .newInstance(member.getId(), member.getOrganization(), member.getFirstName(), member.getLastName(),
                        member.getMobile(), member.getEmail(), jwtResponse);
        return memberAuthResponseData;
    }

    @Override
    public AdministrationUserAuthResponseData authenticateAdminUser(AdministrationUserLoginRequest loginRequest) {
        final AdministrationAuthTokenDTO administrationAuthTokenDTO = new AdministrationAuthTokenDTO();
        administrationAuthTokenDTO.setUserName(loginRequest.getUserName().trim());
        if(loginRequest.getOrgId() != null) { // for super admin
            administrationAuthTokenDTO.setOrgId(loginRequest.getOrgId());
        }
        Authentication authentication = authenticationManager.authenticate(
                new AdministrationUserUsernameAndPasswordAuthToken(administrationAuthTokenDTO,
                        loginRequest.getPassword().trim()));
        AdministrationUserDetailsImpl adminDetails = (AdministrationUserDetailsImpl) authentication.getPrincipal();
        final AdministrationUser administrationUser = administrationUserRepositoryWrapper.findOneWithNotFoundDetection(adminDetails.getId());
        Long organizationId = null;
        if(administrationUser.getOrganization() != null) {
            organizationId = administrationUser.getOrganization().getId();
        }
        String jwtToken = jwtTokenHandler.generateAdministrationUserJwtToken(administrationUser.getAccount().getEmail(),
                organizationId, administrationUser.getId());
        JwtResponse jwtResponse =  new JwtResponse(jwtToken);
        List<RoleData> roles = new ArrayList<>();
        for(Role role: administrationUser.getAccount().getRoles()) {
            roles.add(role.toData());
        }
        return AdministrationUserAuthResponseData.newInstance(administrationUser.getId(), administrationUser.getFirstName(),
                administrationUser.getLastName(), administrationUser.getAccount().getEmail(),
                administrationUser.getAccount().getMobile(), roles, jwtResponse);
    }

    @Override
    public Member delete(Long id) {
        final Member member = this.memberRepositoryWrapper.findOneWithNotFoundDetection(id);
        member.setIsDeleted(true);
        member.setDeletedOn(LocalDate.now());
        member.setDeletedBy(1);
        this.memberRepository.saveAndFlush(member);
        return member;
    }
}
