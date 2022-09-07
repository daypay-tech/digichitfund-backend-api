package com.daypaytechnologies.digichitfund.app.organization.domain;

import com.daypaytechnologies.digichitfund.app.organization.request.CreateOrganizationRequest;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Organization")
@Table(name = "organization")
@Data
public class Organization {

    @Id
    @GeneratedValue(generator = "org_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "org_gen", sequenceName = "org_gen_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "org_name", nullable = false, unique = true)
    @Basic(optional = false)
    private String orgName;

    @Column(name = "org_website")
    private String orgWebsite;

    @Column(name = "is_deleted")
    @Basic
    private Boolean isDeleted;

    public static Organization from(CreateOrganizationRequest createOrganizationRequest) {
        final Organization organization = new Organization();
        organization.setOrgName(createOrganizationRequest.getOrgName());
        return organization;
    }
}
