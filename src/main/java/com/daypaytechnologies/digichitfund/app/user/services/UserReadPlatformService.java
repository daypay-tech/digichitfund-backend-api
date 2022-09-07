package com.daypaytechnologies.digichitfund.app.user.services;

import com.daypaytechnologies.digichitfund.infrastructure.pagination.Page;
import com.daypaytechnologies.digichitfund.app.user.data.MemberData;
import com.daypaytechnologies.digichitfund.app.user.data.UserData;

public interface UserReadPlatformService {

    Page<MemberData> fetchAll(int page, int pageSize);

    Page<UserData> fetchAdministrationUsers(int page, int pageSize);
}
