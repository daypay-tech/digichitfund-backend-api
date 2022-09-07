package com.daypaytechnologies.digichitfund.app.user.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.user.data.AdministrationUserAuthResponseData;
import com.daypaytechnologies.digichitfund.app.user.data.MemberAuthResponseData;
import com.daypaytechnologies.digichitfund.app.user.domain.member.Member;
import com.daypaytechnologies.digichitfund.app.user.request.AdministrationUserLoginRequest;
import com.daypaytechnologies.digichitfund.app.user.request.AdministrationUserSignUpRequest;
import com.daypaytechnologies.digichitfund.app.user.request.LoginRequest;
import com.daypaytechnologies.digichitfund.app.user.request.MemberSignUpRequest;

public interface UserWritePlatformService {

    Response doMemberSignUp(final MemberSignUpRequest memberSignUpRequest);

    Response doAdministrationUserSignUp(AdministrationUserSignUpRequest administrationUserSignUpRequest);

    Response createSuperAdminAccount(AdministrationUserSignUpRequest administrationUserSignUpRequest);

    MemberAuthResponseData authenticateMember(LoginRequest loginRequest);

    AdministrationUserAuthResponseData authenticateAdminUser(AdministrationUserLoginRequest loginRequest);

    Member delete(Long id);
}
