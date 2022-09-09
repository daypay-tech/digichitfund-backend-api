package com.daypaytechnologies.digichitfund.security.authprovider;

import com.daypaytechnologies.digichitfund.security.MemberUserDetailsImpl;
import com.daypaytechnologies.digichitfund.security.MemberUserDetailsServiceImpl;
import com.daypaytechnologies.digichitfund.security.authtoken.MemberUsernameAndPasswordAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberAuthenticationProvider implements AuthenticationProvider {

    private final MemberUserDetailsServiceImpl memberUserDetailsService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(MemberUsernameAndPasswordAuthToken.class);
    }
}
