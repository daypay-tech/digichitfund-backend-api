package com.daypaytechnologies.digichitfund.app.user.services;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.DuplicateRecordException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;
import com.daypaytechnologies.digichitfund.app.user.constants.RoleConstants;
import com.daypaytechnologies.digichitfund.app.user.domain.account.Account;
import com.daypaytechnologies.digichitfund.app.user.domain.account.AccountRepository;
import com.daypaytechnologies.digichitfund.app.user.domain.role.Role;
import com.daypaytechnologies.digichitfund.app.user.domain.role.RoleRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.user.request.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountWritePlatformServiceImpl implements AccountWritePlatformService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepositoryWrapper roleRepositoryWrapper;

    @Override
    @Transactional
    public Account saveAccount(CreateAccountRequest createAccountRequest) {
        checkSuperAdminUserAlreadyExist(createAccountRequest.getRoles());
        final List<Role> roleList = new ArrayList<>();
        for(Long roleId: createAccountRequest.getRoles()) {
            final Role memberRole = roleRepositoryWrapper.findOneWithNotFoundDetection(roleId);
            roleList.add(memberRole);
        }
        Account account = Account.from(createAccountRequest.getEmail(),
                createAccountRequest.getMobile(), createAccountRequest.getPassword(),
                roleList, passwordEncoder);
        return this.accountRepository.saveAndFlush(account);
    }

    @Override
    @Transactional
    public Account saveSuperAdminAccount(CreateAccountRequest createAccountRequest) {
        createAccountRequest.setRoles(new ArrayList<>());
        final Role role = this.roleRepositoryWrapper.findOneWithNotFoundDetection(RoleConstants.ROLE_SUPER_ADMIN);
        createAccountRequest.getRoles().add(role.getId());
        return saveAccount(createAccountRequest);
    }

    public void checkSuperAdminUserAlreadyExist(final List<Long> roles) {
        boolean hasSuperAdminRole = false;
        for(Long roleId: roles) {
            final Role memberRole = this.roleRepositoryWrapper.findOneWithNotFoundDetection(roleId);
            if(memberRole.getCode().equals(RoleConstants.ROLE_SUPER_ADMIN)) {
                hasSuperAdminRole = true;
                break;
            }
        }
        if(hasSuperAdminRole) {
            final List<Account> accounts = accountRepository.findAll();
            boolean isSuperAdminRoleExist = false;
            outer: for(Account account: accounts) {
                Set<Role> roleList = account.getRoles();
                for(Role role: roleList) {
                    if(role.getCode().equals(RoleConstants.ROLE_SUPER_ADMIN)) {
                        isSuperAdminRoleExist = true;
                        break outer;
                    }
                }
            }
            if(isSuperAdminRoleExist) {
                throw new DuplicateRecordException("Role Super Admin Already Exists");
            }
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
}
