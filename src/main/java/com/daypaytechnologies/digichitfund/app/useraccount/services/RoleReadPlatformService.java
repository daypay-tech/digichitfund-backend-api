package com.daypaytechnologies.digichitfund.app.useraccount.services;


import com.daypaytechnologies.digichitfund.app.useraccount.data.RoleData;

import java.util.List;

public interface RoleReadPlatformService {

    List<RoleData> fetchAll();

}
