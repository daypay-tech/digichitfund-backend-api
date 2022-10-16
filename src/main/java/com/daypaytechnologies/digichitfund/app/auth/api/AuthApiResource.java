package com.daypaytechnologies.digichitfund.app.auth.api;

import com.daypaytechnologies.digichitfund.app.auth.data.AuthData;
import com.daypaytechnologies.digichitfund.app.auth.request.UserLoginRequest;
import com.daypaytechnologies.digichitfund.app.auth.services.AuthService;
import com.daypaytechnologies.digichitfund.app.user.data.AdministrationUserAuthResponseData;
import com.daypaytechnologies.digichitfund.app.user.services.UserAccountReadPlatformService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "AuthApiResource")
public class AuthApiResource {

    private final AuthService authService;

    @PostMapping("/login")
    @SecurityRequirements(value = {})
    public AuthData login(@RequestBody UserLoginRequest loginRequest) {
        return this.authService.doLogin(loginRequest);
    }
}
