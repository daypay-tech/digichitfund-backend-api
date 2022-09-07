package com.daypaytechnologies.digichitfund.app.user.services;

import com.daypaytechnologies.digichitfund.app.user.domain.account.Account;
import com.daypaytechnologies.digichitfund.app.user.request.CreateAccountRequest;

public interface AccountWritePlatformService {

    Account saveAccount(CreateAccountRequest createAccountRequest);

    Account saveSuperAdminAccount(CreateAccountRequest createAccountRequest);
}
