package com.daypaytechnologies.digichitfund.app.useraccount.api;

import com.daypaytechnologies.digichitfund.app.useraccount.domain.account.UserAccount;
import com.daypaytechnologies.digichitfund.app.useraccount.request.CreateUserAccountRequest;
import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.useraccount.services.UserAccountWritePlatformService;
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

    @PostMapping
    @SecurityRequirements(value = {})
    public Response createAccount(@RequestBody CreateUserAccountRequest administrationUserSignUpRequest) {
        UserAccount userAccount = userAccountWritePlatformService.createUserAccount(administrationUserSignUpRequest);
        return Response.of(userAccount.getId());
    }
}
