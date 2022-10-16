package com.daypaytechnologies.digichitfund.security;

import com.daypaytechnologies.digichitfund.app.user.domain.account.UserAccount;
import com.daypaytechnologies.digichitfund.app.user.domain.account.UserAccountRepository;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("administrationUserDetailsService")
@Slf4j
@RequiredArgsConstructor
public class UserAccountDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Transactional
    @Override
    public UserAccountDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount;
        userAccount = userAccountRepository.findByUsername(username);
        if(userAccount == null) {
            throw new AuthenticationException("Either Username or password is wrong. please login again");
        }
        return UserAccountDetailsImpl.build(userAccount);
    }
}
