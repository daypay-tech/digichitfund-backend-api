package com.daypaytechnologies.digichitfund.app.user.rowmapper;

import com.daypaytechnologies.digichitfund.app.user.data.UserData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserData> {

    private final String schema;

    public UserRowMapper() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("FROM administration_user u ");
        builder.append("INNER JOIN account ua ON ua.id = u.account_id ");
        this.schema = builder.toString();
    }

    public String schema() {
        return this.schema;
    }

    public String tableSchema() {
        final StringBuilder builder = new StringBuilder(200);
        builder.append("u.id as userId, ");
        builder.append("u.first_name as firstName, ");
        builder.append("ua.email as email, ");
        builder.append("ua.mobile as mobile, ");
        builder.append("u.last_name as lastName ");
        builder.append(this.schema);
        return builder.toString();
    }

    @Override
    public UserData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final long id = rs.getLong("userId");
        final String email = rs.getString("email");
        final String mobile = rs.getString("mobile");
        final String firstName = rs.getString("firstName");
        final String lastName = rs.getString("lastName");
        return UserData.newInstance(id, firstName, lastName, email, mobile);
    }
}
