package com.daypaytechnologies.digichitfund.app.organization.data;

import lombok.Data;

@Data
public class OrganizationData {

    private Long id;

    private String orgName;

    public OrganizationData(final Long id, final String orgName) {
        this.id = id;
        this.orgName = orgName;
    }

    public static OrganizationData newInstance(final Long id, final String orgName) {
        return new OrganizationData(id, orgName);
    }
}
