package com.daypaytechnologies.digichitfund.app.organization.services;

import com.daypaytechnologies.digichitfund.app.organization.data.OrganizationData;

import java.util.List;

public interface OrganizationReadPlatformService {

    OrganizationData fetchById(final Long orgId);

    List<OrganizationData> fetchAll();
}
