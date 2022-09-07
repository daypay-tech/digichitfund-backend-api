package com.daypaytechnologies.digichitfund.app.organization.domain;

import com.daypaytechnologies.digichitfund.infrastructure.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrganizationRepositoryWrapper {

    private final OrganizationRepository organizationRepository;

    @Transactional(readOnly = true)
    public Organization findOneWithNotFoundDetection(final Long orgId) {
        return this.organizationRepository.findById(orgId).orElseThrow(() -> new NotFoundException("Organization  Not found " + orgId));
    }
}
