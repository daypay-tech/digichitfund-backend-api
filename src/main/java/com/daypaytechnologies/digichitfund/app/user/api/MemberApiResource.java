package com.daypaytechnologies.digichitfund.app.user.api;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.app.user.data.MemberAuthResponseData;
import com.daypaytechnologies.digichitfund.app.user.data.MemberData;
import com.daypaytechnologies.digichitfund.app.user.domain.member.Member;
import com.daypaytechnologies.digichitfund.app.user.request.LoginRequest;
import com.daypaytechnologies.digichitfund.app.user.request.MemberSignUpRequest;
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
@RequestMapping("/api/v1/members")
@Tag(name = "MemberApiResource")
public class MemberApiResource {

    private final UserReadPlatformService userReadPlatformService;

    private final UserWritePlatformService userWritePlatformService;

    @GetMapping
    public Page<MemberData> fetchAll(@RequestParam (name = "page", required = false, defaultValue = "1") int page,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return this.userReadPlatformService.fetchAll(page, pageSize);
    }

    @PostMapping("/signUp")
    @SecurityRequirements(value = {})
    public Response signUp(@RequestBody MemberSignUpRequest memberSignUpRequest) {
        return userWritePlatformService.doMemberSignUp(memberSignUpRequest);
    }

    @PostMapping("/login")
    @SecurityRequirements(value = {})
    public MemberAuthResponseData authenticateMember(@RequestBody LoginRequest loginRequest) {
        return this.userWritePlatformService.authenticateMember(loginRequest);
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        final Member member = this.userWritePlatformService.delete(id);
        return Response.of(member.getId());
    }
}
