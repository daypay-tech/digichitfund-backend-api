package com.daypaytechnologies.digichitfund.security.authtoken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AdministrationUserUsernameAndPasswordAuthToken extends UsernamePasswordAuthenticationToken {

    public AdministrationUserUsernameAndPasswordAuthToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AdministrationUserUsernameAndPasswordAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
