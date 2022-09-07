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
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        final MemberUserDetailsImpl userDetails = memberUserDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new com.daypaytechnologies.digichitfund.infrastructure.exceptions.AuthenticationException("Either username or password wrong");
        }
        return new MemberUsernameAndPasswordAuthToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(MemberUsernameAndPasswordAuthToken.class);
    }
}
