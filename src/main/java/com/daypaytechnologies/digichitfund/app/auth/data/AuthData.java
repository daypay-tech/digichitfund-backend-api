package com.daypaytechnologies.digichitfund.app.auth.data;

import com.daypaytechnologies.digichitfund.app.user.data.AdministrationUserAuthResponseData;
import com.daypaytechnologies.digichitfund.app.user.data.JwtResponse;
import com.daypaytechnologies.digichitfund.app.user.data.RoleData;
import com.daypaytechnologies.digichitfund.app.user.data.UserData;
import io.jsonwebtoken.Jwt;
import lombok.Data;
import org.apache.catalina.User;

import java.util.List;

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
