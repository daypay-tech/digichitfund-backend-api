package com.daypaytechnologies.digichitfund.security.authprovider;

import com.daypaytechnologies.digichitfund.app.user.dto.AdministrationAuthTokenDTO;
import com.daypaytechnologies.digichitfund.security.AdministrationUserDetailsImpl;
import com.daypaytechnologies.digichitfund.security.AdministrationUserDetailsServiceImpl;
import com.daypaytechnologies.digichitfund.security.authtoken.AdministrationUserUsernameAndPasswordAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdministrationUserAuthenticationProvider implements AuthenticationProvider  {

    private final AdministrationUserDetailsServiceImpl administrationUserDetailsService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
