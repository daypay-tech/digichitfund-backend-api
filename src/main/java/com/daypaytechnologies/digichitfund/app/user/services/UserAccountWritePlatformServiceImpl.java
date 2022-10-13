package com.daypaytechnologies.digichitfund.app.user.services;

import com.daypaytechnologies.digichitfund.app.user.domain.account.UserAccountRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.user.request.CreateUserAccountRequest;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;
import com.daypaytechnologies.digichitfund.app.user.data.*;
import com.daypaytechnologies.digichitfund.app.user.domain.account.UserAccount;
import com.daypaytechnologies.digichitfund.app.user.domain.account.UserAccountRepository;
import com.daypaytechnologies.digichitfund.app.user.domain.role.Role;
import com.daypaytechnologies.digichitfund.app.user.domain.role.RoleRepositoryWrapper;
import com.daypaytechnologies.digichitfund.security.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountWritePlatformServiceImpl implements UserAccountWritePlatformService {

    private final PasswordEncoder passwordEncoder;

    private final RoleRepositoryWrapper roleRepositoryWrapper;

    private final UserAccountDataValidator userAccountDataValidator;

    private final JwtTokenHandler jwtTokenHandler;

    private final AuthenticationManager authenticationManager;

    private final UserAccountRepository userAccountRepository;

    private final UserAccountRepositoryWrapper userAccountRepositoryWrapper;

    @Override
    @Transactional
    public UserAccount createUserAccount(CreateUserAccountRequest createUserAccountRequest) {
        try {
            final UserAccount oldUserAccount = this.userAccountRepositoryWrapper.findOneWithNotFoundDetection(createUserAccountRequest.getEmail(),
                    createUserAccountRequest.getMobile());
            if(oldUserAccount != null) {
                return oldUserAccount;
            }
            final List<Role> roles = new ArrayList<>();
            for(Long roleId: createUserAccountRequest.getRoles()) {
                final Role role = roleRepositoryWrapper.findOneWithNotFoundDetection(roleId);
                roles.add(role);
            }
            final UserAccount userAccount = UserAccount.from(createUserAccountRequest, roles, passwordEncoder);
            this.userAccountRepository.saveAndFlush(userAccount);
            return userAccount;
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            dve.getRootCause();
            final String mobileNo = createUserAccountRequest.getMobile();
            final String emailId = createUserAccountRequest.getEmail();
            if (dve.getMostSpecificCause().getMessage().contains("mobile")) {
                throw new PlatformDataIntegrityException("error.msg.client.duplicate.mobileNo",
                        "User with mobileNo `" + mobileNo + "` already exists", "mobileNo", mobileNo);
            }  else if (dve.getMostSpecificCause().getMessage().contains("email")) {
                throw new PlatformDataIntegrityException("error.msg.client.duplicate.email",
                        "User with email `" + emailId + "` already exists", "email", emailId);
            }
            throw new PlatformDataIntegrityException("error.msg.client.unknown.data.integrity.issue",
                    "Unknown data integrity issue with resource.");
        } catch (final PersistenceException dve) {
            Throwable realCause = ExceptionUtils.getRootCause(dve.getCause());
            final String mobileNo = createUserAccountRequest.getMobile();
            final String emailId = createUserAccountRequest.getEmail();
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
    }
}
