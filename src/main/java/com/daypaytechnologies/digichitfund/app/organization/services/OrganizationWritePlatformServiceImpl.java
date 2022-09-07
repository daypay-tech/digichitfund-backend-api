package com.daypaytechnologies.digichitfund.app.organization.services;

import com.daypaytechnologies.digichitfund.infrastructure.Response;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.DuplicateRecordException;
import com.daypaytechnologies.digichitfund.infrastructure.exceptions.PlatformDataIntegrityException;
import com.daypaytechnologies.digichitfund.app.organization.domain.Organization;
import com.daypaytechnologies.digichitfund.app.organization.domain.OrganizationRepository;
import com.daypaytechnologies.digichitfund.app.organization.domain.OrganizationRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.organization.request.CreateOrganizationRequest;
import com.daypaytechnologies.digichitfund.app.organization.request.OrganizationRequestDataValidator;
import com.daypaytechnologies.digichitfund.app.organization.request.UpdateOrganizationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrganizationWritePlatformServiceImpl implements OrganizationWritePlatformService {

    private final OrganizationRepository organizationRepository;

    private final OrganizationRequestDataValidator requestDataValidator;

    private final OrganizationRepositoryWrapper organizationRepositoryWrapper;

    @Override
    @Transactional
    public Response save(CreateOrganizationRequest createOrganizationRequest) {
        this.requestDataValidator.validateCreateOrg(createOrganizationRequest);
        try {
            final Organization existingOrganization = this.organizationRepository.findByOrgName(createOrganizationRequest.getOrgName().toLowerCase());
            if(existingOrganization != null) {
                throw new DuplicateRecordException("Organization name already exist "+existingOrganization.getOrgName());
            }
            final Organization organization = Organization.from(createOrganizationRequest);
            this.organizationRepository.saveAndFlush(organization);
            return Response.of(organization.getId());
        } catch (DataIntegrityViolationException e) {
            throw new PlatformDataIntegrityException("error.duplicate.data", String.format("Org Name %s already exist", createOrganizationRequest.getOrgName()));
        }
    }

    @Override
    @Transactional
    public Response update(Long id, UpdateOrganizationRequest updateOrganizationRequest) {
        this.requestDataValidator.validateUpdateOrg(updateOrganizationRequest);
        try {
            final Organization existingOrganization = this.organizationRepository.findByOrgNameNotInThisId(id, updateOrganizationRequest.getOrgName());
            if(existingOrganization != null) {
                throw new DuplicateRecordException("Organization name already exist "+existingOrganization.getOrgName().toLowerCase());
            }
            final Organization organization = organizationRepositoryWrapper.findOneWithNotFoundDetection(id);
            organization.setOrgName(updateOrganizationRequest.getOrgName());
            this.organizationRepository.saveAndFlush(organization);
            return Response.of(organization.getId());
        } catch (DataIntegrityViolationException e) {
            throw new PlatformDataIntegrityException("error.duplicate.data", String.format("Org Name %s already exist", updateOrganizationRequest.getOrgName()));
        }
    }
}
