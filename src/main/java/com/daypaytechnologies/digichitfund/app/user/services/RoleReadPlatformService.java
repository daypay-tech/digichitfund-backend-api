package com.daypaytechnologies.digichitfund.app.user.services;


import com.daypaytechnologies.digichitfund.app.user.data.RoleData;

import java.util.List;

public interface RoleReadPlatformService {

    List<RoleData> fetchAll();

}
