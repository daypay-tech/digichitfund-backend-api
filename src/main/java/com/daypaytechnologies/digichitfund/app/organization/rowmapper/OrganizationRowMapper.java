package com.daypaytechnologies.digichitfund.app.organization.rowmapper;

import com.daypaytechnologies.digichitfund.app.organization.data.OrganizationData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationRowMapper implements RowMapper<OrganizationData> {

    private final String schema;

    public OrganizationRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM organization o ");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("o.id as id, ");
        builder.append("o.org_name as orgName ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public OrganizationData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("id");
        final String orgName = rs.getString("orgName");
        return OrganizationData.newInstance(id, orgName);
    }
}
