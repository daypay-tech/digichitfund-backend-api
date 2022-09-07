package com.daypaytechnologies.digichitfund.app.user.rowmapper;

import com.daypaytechnologies.digichitfund.app.user.data.MemberData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<MemberData> {

    private final String schema;

    public MemberRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM member m ");
        this.schema = builder.toString();
    }
    public String schema() {
        return this.schema;
    }
    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("m.id as id, ");
        builder.append("m.organization as organization, ");
        builder.append("m.first_name as firstName, ");
        builder.append("m.last_name as lastName, ");
        builder.append("m.mobile as mobile, ");
        builder.append("m.email as email ");
        builder.append(this.schema);
        return builder.toString();
    }
   @Override
    public MemberData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("id");
        final String organization = rs.getString("organization");
        final String firstName = rs.getString("firstName");
        final String lastName = rs.getString("lastName");
        final String mobile = rs.getString("mobile");
        final String email = rs.getString("email");
       return MemberData.newInstance(id, organization, firstName, lastName, mobile, email);
    }
}
