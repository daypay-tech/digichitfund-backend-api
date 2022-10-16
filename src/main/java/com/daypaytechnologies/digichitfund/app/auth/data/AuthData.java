package com.daypaytechnologies.digichitfund.app.auth.data;

import com.daypaytechnologies.digichitfund.app.useraccount.data.JwtResponse;
import com.daypaytechnologies.digichitfund.app.useraccount.data.UserData;
import lombok.Data;

@Data
public class AuthData {

    private UserData userData;

    private JwtResponse token;

    public AuthData(final UserData userData, final JwtResponse token) {
        this.userData = userData;
        this.token = token;
    }

    public static AuthData newInstance(final UserData userData, final JwtResponse jwtResponse) {
        return new AuthData(userData, jwtResponse);
    }
}
