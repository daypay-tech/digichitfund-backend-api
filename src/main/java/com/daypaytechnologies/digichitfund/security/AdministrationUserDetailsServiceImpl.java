package com.daypaytechnologies.digichitfund.security;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.AuthenticationException;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("administrationUserDetailsService")
@Slf4j
@RequiredArgsConstructor
public class AdministrationUserDetailsServiceImpl implements UserDetailsService {

    private final AdministrationUserRepository administrationUserRepository;

    @Transactional
    @Override
    public AdministrationUserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] userNamesOrg = username.split(":");
        username = userNamesOrg[0];
        long orgId = Long.parseLong(userNamesOrg[1]);
        AdministrationUser user;
        if(orgId == 0) {
            user = administrationUserRepository.findByUsernameForSuperAdmin(username);
        } else {
            user = administrationUserRepository.findByUsername(username, orgId);
        }
        if(user == null) {
            throw new AuthenticationException("Either Username or password is wrong. please login again");
        }
        return AdministrationUserDetailsImpl.build(user);
    }
}
