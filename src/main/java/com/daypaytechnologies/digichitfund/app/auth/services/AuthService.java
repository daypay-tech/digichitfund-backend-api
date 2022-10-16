package com.daypaytechnologies.digichitfund.app.auth.services;

import com.daypaytechnologies.digichitfund.app.auth.data.AuthData;
import com.daypaytechnologies.digichitfund.app.auth.request.UserLoginRequest;

public interface AuthService {

    AuthData doLogin(UserLoginRequest userLoginRequest);
}
