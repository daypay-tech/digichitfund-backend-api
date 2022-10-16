package com.daypaytechnologies.digichitfund.app.useraccount.services;

import com.daypaytechnologies.digichitfund.app.useraccount.domain.account.UserAccount;
import com.daypaytechnologies.digichitfund.app.useraccount.request.CreateUserAccountRequest;

public interface UserAccountWritePlatformService {

    UserAccount createUserAccount(final CreateUserAccountRequest createUserAccountRequest);

}
