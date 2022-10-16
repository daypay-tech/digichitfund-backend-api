package com.daypaytechnologies.digichitfund.security.authtoken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAccountUsernameAndPasswordAuthToken extends UsernamePasswordAuthenticationToken {

    public UserAccountUsernameAndPasswordAuthToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UserAccountUsernameAndPasswordAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
