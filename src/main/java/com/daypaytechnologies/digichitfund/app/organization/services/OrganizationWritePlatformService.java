package com.daypaytechnologies.digichitfund.app.organization.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.app.organization.request.CreateOrganizationRequest;
import com.daypaytechnologies.digichitfund.app.organization.request.UpdateOrganizationRequest;

public interface OrganizationWritePlatformService {

    Response save(CreateOrganizationRequest createOrganizationRequest);

    Response update(Long id, UpdateOrganizationRequest updateOrganizationRequest);
}
