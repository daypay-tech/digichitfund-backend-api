package com.daypaytechnologies.digichitfund.app.user.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.app.user.data.*;
import com.daypaytechnologies.digichitfund.app.user.request.AdministrationUserLoginRequest;
import com.daypaytechnologies.digichitfund.app.user.request.AdministrationUserSignUpRequest;
import com.daypaytechnologies.digichitfund.app.user.services.UserReadPlatformService;
import com.daypaytechnologies.digichitfund.app.user.services.UserWritePlatformService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/administrations")
@Tag(name = "AdministratorApiResource")
public class AdministrationUserApiResource {

    private final UserWritePlatformService userWritePlatformService;

    private final UserReadPlatformService userReadPlatformService;

    @GetMapping("/users")
    public Page<UserData> fetchAdministrationUsers(@RequestParam (name = "page", required = false, defaultValue = "1") int page,
                                                 @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return this.userReadPlatformService.fetchAdministrationUsers(page, pageSize);
    }

    @PostMapping("/signUp")
    @SecurityRequirements(value = {})
    public Response signUp(@RequestBody AdministrationUserSignUpRequest administrationUserSignUpRequest) {
        return userWritePlatformService.doAdministrationUserSignUp(administrationUserSignUpRequest);
    }

    @PostMapping("/createSuperAdminAccount")
    @SecurityRequirements(value = {})
    public Response createSuperAdminAccount(@RequestBody AdministrationUserSignUpRequest administrationUserSignUpRequest) {
        return userWritePlatformService.createSuperAdminAccount(administrationUserSignUpRequest);
    }

    @PostMapping("/login")
    @SecurityRequirements(value = {})
    public AdministrationUserAuthResponseData authenticateAdminUser(@RequestBody AdministrationUserLoginRequest loginRequest) {
        return this.userWritePlatformService.authenticateAdminUser(loginRequest);
    }
}
