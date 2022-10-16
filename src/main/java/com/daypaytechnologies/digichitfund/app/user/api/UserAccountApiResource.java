package com.daypaytechnologies.digichitfund.app.user.api;

import com.daypaytechnologies.digichitfund.app.user.domain.account.UserAccount;
import com.daypaytechnologies.digichitfund.app.user.request.CreateUserAccountRequest;
import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.app.user.data.*;
import com.daypaytechnologies.digichitfund.app.user.services.UserAccountReadPlatformService;
import com.daypaytechnologies.digichitfund.app.user.services.UserAccountWritePlatformService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@Tag(name = "UserAccountApiResource")
public class UserAccountApiResource {

    private final UserAccountWritePlatformService userAccountWritePlatformService;

    private final UserAccountReadPlatformService userAccountReadPlatformService;

//    @GetMapping("/users")
//    public Page<UserData> fetchAdministrationUsers(@RequestParam (name = "page", required = false, defaultValue = "1") int page,
//                                                 @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
//        return this.userAccountReadPlatformService.fetchAdministrationUsers(page, pageSize);
//    }

    @PostMapping
    @SecurityRequirements(value = {})
    public Response createAccount(@RequestBody CreateUserAccountRequest administrationUserSignUpRequest) {
        UserAccount userAccount = userAccountWritePlatformService.createUserAccount(administrationUserSignUpRequest);
        return Response.of(userAccount.getId());
    }

//    @PostMapping("/createSuperAdminAccount")
//    @SecurityRequirements(value = {})
//    public Response createSuperAdminAccount(@RequestBody AdministrationUserSignUpRequest administrationUserSignUpRequest) {
//        return userAccountWritePlatformService.createSuperAdminAccount(administrationUserSignUpRequest);
//    }
}
