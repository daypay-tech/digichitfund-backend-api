package com.daypaytechnologies.digichitfund.security.authprovider;

import com.daypaytechnologies.digichitfund.app.auth.dto.UserAccountAuthTokenDTO;
import com.daypaytechnologies.digichitfund.security.UserAccountDetailsImpl;
import com.daypaytechnologies.digichitfund.security.UserAccountDetailsServiceImpl;
import com.daypaytechnologies.digichitfund.security.authtoken.UserAccountUsernameAndPasswordAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccountAuthenticationProvider implements AuthenticationProvider  {

    private final UserAccountDetailsServiceImpl userAccountDetailsService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAccountAuthTokenDTO administrationAuthTokenDTO = (UserAccountAuthTokenDTO) authentication.getPrincipal();
        String username = administrationAuthTokenDTO.getUsername();
        String password = authentication.getCredentials().toString();
        final UserAccountDetailsImpl userDetails = userAccountDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new com.daypaytechnologies.digichitfund.infrastructure.exceptions.AuthenticationException("Either username or password wrong");
        }
        return new UserAccountUsernameAndPasswordAuthToken(userDetails,
                userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UserAccountUsernameAndPasswordAuthToken.class);
    }
}
