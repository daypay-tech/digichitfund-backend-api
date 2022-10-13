package com.daypaytechnologies.digichitfund.app.user.services;

import com.daypaytechnologies.digichitfund.app.user.domain.account.UserAccount;
import com.daypaytechnologies.digichitfund.app.user.request.CreateUserAccountRequest;

public interface UserAccountWritePlatformService {

    UserAccount createUserAccount(final CreateUserAccountRequest createUserAccountRequest);

}
