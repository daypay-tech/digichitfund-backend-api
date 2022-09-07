package com.daypaytechnologies.digichitfund.app.organization.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("SELECT o FROM Organization o WHERE LOWER(o.orgName) =:orgName")
    Organization findByOrgName(@Param("orgName") String orgName);

    @Query("SELECT o FROM Organization o WHERE o.id <>:orgId AND LOWER(o.orgName) =:orgName")
    Organization findByOrgNameNotInThisId(@Param("orgId") Long orgId, @Param("orgName") String orgName);
}
